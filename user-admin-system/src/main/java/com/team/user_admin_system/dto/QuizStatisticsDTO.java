package com.team.user_admin_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 答题统计数据DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizStatisticsDTO {

    /**
     * 图表标签
     */
    private List<String> labels;

    /**
     * 图表数据
     */
    private List<Integer> data;

    /**
     * 图表类型
     */
    private String chartType;

    /**
     * 图表标题
     */
    private String title;
}