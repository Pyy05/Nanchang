package com.team.user_admin_system.module.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "option_a", length = 200)
    private String optionA;

    @Column(name = "option_b", length = 200)
    private String optionB;

    @Column(name = "option_c", length = 200)
    private String optionC;

    @Column(name = "option_d", length = 200)
    private String optionD;

    @Column(length = 20, nullable = false)
    private String answer;

    @Column(columnDefinition = "TEXT")
    private String analysis;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "content_id")
    private Integer contentId;

    private Integer score;

    public enum ContentType {
        event, person
    }
}