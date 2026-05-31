package com.team.user_admin_system.module.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String reply;

    private Integer status;

    private LocalDateTime createTime;

    private Integer userId;
}