package com.team.user_admin_system.module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.user_admin_system.entity.Timeline;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Integer> {
}