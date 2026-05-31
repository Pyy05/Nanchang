package com.team.user_admin_system.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "timeline")
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nodeId;

    private String timePoint;
    private String description;
    private Integer eventId;
    @Transient  // 加这一行！表示数据库不存在此字段
    private String eventName;
}