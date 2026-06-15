package com.team.user_admin_system.module.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "edit_log")
public class EditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_name")
    private String targetName;

    @Column(name = "editor")
    private String editor;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "target_type")
    private String targetType;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "status")
    private String status;

    public EditLog() {}

    public EditLog(String targetName, String editor, String actionType, String targetType, String status) {
        this.targetName = targetName;
        this.editor = editor;
        this.actionType = actionType;
        this.targetType = targetType;
        this.status = status;
        this.createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }

    public String getEditor() { return editor; }
    public void setEditor(String editor) { this.editor = editor; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
