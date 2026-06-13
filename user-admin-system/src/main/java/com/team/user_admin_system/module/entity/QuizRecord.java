package com.team.user_admin_system.module.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 答题记录实体类
 * 记录用户每次答题的详细信息
 */
@Entity
@Table(name = "quiz_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 题目ID
     */
    private Integer questionId;

    /**
     * 用户答案
     */
    @Column(length = 10)
    private String userAnswer;

    /**
     * 是否正确
     */
    private Boolean isCorrect;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 答题时间
     */
    private LocalDateTime createTime;

    /**
     * 题目类型（event/person）
     */
    @Column(length = 20)
    private String contentType;
}