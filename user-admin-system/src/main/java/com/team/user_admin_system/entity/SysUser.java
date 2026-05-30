package com.team.user_admin_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user") 
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; 

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String phone;
    private Integer points; 
}