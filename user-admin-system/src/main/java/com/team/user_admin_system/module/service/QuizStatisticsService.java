package com.team.user_admin_system.module.service;

import com.team.user_admin_system.dto.QuizStatisticsDTO;
import com.team.user_admin_system.module.entity.QuizRecord;
import com.team.user_admin_system.module.repository.QuizRecordRepository;
import com.team.user_admin_system.module.repository.QuestionRepository;
import com.team.user_admin_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizStatisticsService {

    private final QuizRecordRepository quizRecordRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public QuizStatisticsService(QuizRecordRepository quizRecordRepository, 
                                 UserRepository userRepository,
                                 QuestionRepository questionRepository) {
        this.quizRecordRepository = quizRecordRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public QuizRecord saveRecord(Integer userId, Integer questionId, String userAnswer, 
                                 boolean isCorrect, Integer score, String contentType) {
        QuizRecord record = new QuizRecord();
        record.setUserId(userId);
        record.setQuestionId(questionId);
        record.setUserAnswer(userAnswer);
        record.setIsCorrect(isCorrect);
        record.setScore(isCorrect ? score : 0);
        record.setCreateTime(LocalDateTime.now());
        record.setContentType(contentType);
        return quizRecordRepository.save(record);
    }

    public Map<String, Object> getOverviewStats() {
        Map<String, Object> overview = new HashMap<>();
        
        overview.put("totalQuizCount", quizRecordRepository.count());
        overview.put("totalUserCount", userRepository.count());
        overview.put("totalQuestionCount", questionRepository.count());
        
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().atTime(LocalTime.MAX);
        overview.put("todayQuizCount", quizRecordRepository.countByCreateTimeBetween(todayStart, todayEnd));
        
        long total = quizRecordRepository.count();
        long correct = quizRecordRepository.countByIsCorrect(true);
        double accuracy = total > 0 ? (double) correct / total * 100 : 0;
        overview.put("accuracy", String.format("%.1f", accuracy) + "%");
        
        return overview;
    }

    /**
     * 获取近7天答题数量统计（修复版）
     */
public QuizStatisticsDTO getDailyQuizStats() {
    List<String> labels = new ArrayList<>();
    List<Integer> data = new ArrayList<>();
    
    // 初始化近7天日期
    for (int i = 6; i >= 0; i--) {
        LocalDate date = LocalDate.now().minusDays(i);
        labels.add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
        data.add(0);
    }
    
    // 添加异常处理
    try {
        LocalDateTime startTime = LocalDate.now().minusDays(6).atStartOfDay();
        List<Object[]> results = quizRecordRepository.countRecentDays(startTime);
        
        for (Object[] result : results) {
            if (result.length >= 2) {
                String dateStr = (String) result[0];
                Long count = (Long) result[1];
                int index = labels.indexOf(dateStr);
                if (index != -1) {
                    data.set(index, count.intValue());
                }
            }
        }
    } catch (Exception e) {
        // 日志记录错误，但不抛出异常
        System.err.println("查询每日答题数据失败: " + e.getMessage());
    }
    
    return new QuizStatisticsDTO(labels, data, "line", "近7天答题趋势");
}

    public QuizStatisticsDTO getQuizTypeStats() {
        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        
        try {
            List<Object[]> results = quizRecordRepository.countByContentType();
            
            for (Object[] result : results) {
                if (result.length >= 2) {
                    String type = (String) result[0];
                    Long count = (Long) result[1];
                    labels.add("event".equals(type) ? "事件题" : "人物题");
                    data.add(count.intValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (labels.isEmpty()) {
            labels = List.of("事件题", "人物题");
            data = List.of(0, 0);
        }
        
        return new QuizStatisticsDTO(labels, data, "pie", "答题类型分布");
    }
}