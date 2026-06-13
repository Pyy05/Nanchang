package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.dto.QuizStatisticsDTO;
import com.team.user_admin_system.module.service.QuizStatisticsService;
import com.team.user_admin_system.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 答题统计控制器
 * 提供用户答题行为统计接口
 */
@RestController
@RequestMapping("/quiz/statistics")
public class QuizStatisticsController {

    private final QuizStatisticsService quizStatisticsService;

    public QuizStatisticsController(QuizStatisticsService quizStatisticsService) {
        this.quizStatisticsService = quizStatisticsService;
    }

    /**
     * 获取统计概览
     * 地址：GET /quiz/statistics/overview
     */
    @GetMapping("/overview")
    public Result<Map<String, Object>> getOverview() {
        Map<String, Object> overview = quizStatisticsService.getOverviewStats();
        return Result.success(overview);
    }

    /**
     * 获取每日答题统计（近7天柱状图）
     * 地址：GET /quiz/statistics/daily
     */
    @GetMapping("/daily")
    public Result<QuizStatisticsDTO> getDailyStats() {
        QuizStatisticsDTO stats = quizStatisticsService.getDailyQuizStats();
        return Result.success(stats);
    }

    /**
     * 获取答题类型分布（饼图）
     * 地址：GET /quiz/statistics/type
     */
    @GetMapping("/type")
    public Result<QuizStatisticsDTO> getTypeStats() {
        QuizStatisticsDTO stats = quizStatisticsService.getQuizTypeStats();
        return Result.success(stats);
    }

    

    /**
     * 获取所有答题统计数据（仪表盘）
     * 地址：GET /quiz/statistics/dashboard
     */
    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getAllStats() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("overview", quizStatisticsService.getOverviewStats());
        dashboard.put("daily", quizStatisticsService.getDailyQuizStats());
        dashboard.put("type", quizStatisticsService.getQuizTypeStats());
        return Result.success(dashboard);
    }
}