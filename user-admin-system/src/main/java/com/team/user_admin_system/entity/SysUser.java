package com.team.user_admin_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

/**
 * 实体类：对应数据库里的 user 表
 * 作用：把数据库的一行数据，变成 Java 能看懂的对象
 */
@Data  // Lombok 注解：自动生成 getter、setter、toString 等
@Entity // 告诉Spring：这是数据库实体类（映射表）
@Table(name = "user") // 对应数据库里的表名 = user
public class SysUser {

    @Id // 主键（每张表必须有一个主键）
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增（数据库自动+1）
    private Integer userId; // 用户ID（唯一）

    @Column(unique = true, nullable = false)
    // unique=true：用户名不能重复
    // nullable=false：用户名不能为空
    private String username; // 用户名（登录账号）

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; // 密码（BCrypt加密后）

    private String phone; // 手机号（可选）

    private Integer points; // 积分（用户等级/积分系统）

    // 👇 新增：角色字段（admin / user）
    private String role;
}