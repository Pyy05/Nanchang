package com.team.user_admin_system.module.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;        // 对应数据库 event_id

    private String eventName;    // 对应 event_name
    private String occurTime;    // 对应 occur_time
    private String dynasty;
    private String location;
    private String background;
    private String process;
    private String significance;
    private String relatedPerson; // 对应 related_person
    private String source;
    private String cover;        // 对应数据库 cover 图片字段
}