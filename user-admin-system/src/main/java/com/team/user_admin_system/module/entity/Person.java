package com.team.user_admin_system.module.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    private String name;
    private String dynasty;
    private String birthDeath;
    private String biography;
    private String contribution;
    private String relatedEvent;
    private String source;
    private String cover;
}
