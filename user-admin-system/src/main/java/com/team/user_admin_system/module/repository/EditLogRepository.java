package com.team.user_admin_system.module.repository;

import com.team.user_admin_system.module.entity.EditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditLogRepository extends JpaRepository<EditLog, Long> {
    List<EditLog> findTop10ByOrderByCreateTimeDesc();
}
