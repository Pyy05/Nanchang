package com.team.user_admin_system.module.service.impl;

import com.team.user_admin_system.module.entity.Question;
import com.team.user_admin_system.module.repository.QuestionRepository;
import com.team.user_admin_system.module.service.QuestionService;
import com.team.user_admin_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserService userService;

        /**
     * 获取指定数量的随机问题
     * @param count 要获取的问题数量
     * @return 随机选择的问题列表
     */
        @Override
        public List<Question> getRandomQuestions(int count) {
            // 创建分页请求，从第0页开始，获取指定数量的问题
            return questionRepository.findRandomQuestions(PageRequest.of(0, count));
        }

    @Override
    public List<Question> getRandomEventQuestions(int count) {
        return questionRepository.findRandomByType(Question.ContentType.event, PageRequest.of(0, count));
    }

    @Override
    public List<Question> getRandomPersonQuestions(int count) {
        return questionRepository.findRandomByType(Question.ContentType.person, PageRequest.of(0, count));
    }

    @Override
    public List<Question> getRandomQuestionsByEvent(Integer eventId, int count) {
        return questionRepository.findRandomByContent(Question.ContentType.event, eventId, PageRequest.of(0, count));
    }

    @Override
    public List<Question> getRandomQuestionsByPerson(Integer personId, int count) {
        return questionRepository.findRandomByContent(Question.ContentType.person, personId, PageRequest.of(0, count));
    }

    @Override
    public Map<String, Object> submitAnswer(Integer userId, Integer questionId, String userAnswer) {
        Map<String, Object> result = new HashMap<>();
        
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            result.put("success", false);
            result.put("message", "题目不存在");
            return result;
        }

        boolean isCorrect = question.getAnswer().equalsIgnoreCase(userAnswer);
        result.put("correct", isCorrect);
        result.put("answer", question.getAnswer());
        result.put("analysis", question.getAnalysis());
        result.put("score", question.getScore());

        if (isCorrect && userId != null) {
            userService.addPoints(userId, question.getScore());
            result.put("pointsAdded", question.getScore());
            
            String[] praises = {
                "太棒了！你真是历史小达人！",
                "回答正确！继续加油，你是最棒的！",
                "完美！你的历史知识很扎实！",
                "厉害！看来你对南昌历史很有研究！",
                "正确！继续保持，你就是历史专家！"
            };
            result.put("praise", praises[(int)(Math.random() * praises.length)]);
        } else {
            String[] encouragements = {
                "没关系，再接再厉！学习就是一个积累的过程。",
                "答错了不要紧，重要的是学到了新知识！",
                "加油！下次一定能答对！",
                "别灰心，每个人都是从错误中成长的！",
                "继续努力，你一定会越来越厉害的！"
            };
            result.put("encouragement", encouragements[(int)(Math.random() * encouragements.length)]);
        }

        return result;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }

    @Override
    public Question addQuestion(Question question) {
        if (question.getScore() == null) {
            question.setScore(1);
        }
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Integer id, Question question) {
        Optional<Question> existingQuestion = questionRepository.findById(id);
        if (existingQuestion.isPresent()) {
            Question q = existingQuestion.get();
            q.setContent(question.getContent());
            q.setOptionA(question.getOptionA());
            q.setOptionB(question.getOptionB());
            q.setOptionC(question.getOptionC());
            q.setOptionD(question.getOptionD());
            q.setAnswer(question.getAnswer());
            q.setAnalysis(question.getAnalysis());
            q.setContentType(question.getContentType());
            q.setContentId(question.getContentId());
            if (question.getScore() != null) {
                q.setScore(question.getScore());
            }
            return questionRepository.save(q);
        }
        return null;
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}