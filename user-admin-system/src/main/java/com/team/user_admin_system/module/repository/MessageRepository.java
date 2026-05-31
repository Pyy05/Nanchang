package com.team.user_admin_system.module.repository;

import com.team.user_admin_system.module.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Page<Message> findByStatus(Integer status, Pageable pageable);

    Page<Message> findAllByOrderByCreateTimeDesc(Pageable pageable);

    Page<Message> findByUserIdOrderByCreateTimeDesc(Integer userId, Pageable pageable);
}