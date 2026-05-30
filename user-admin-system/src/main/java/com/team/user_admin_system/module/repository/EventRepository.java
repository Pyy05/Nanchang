package com.team.user_admin_system.module.repository;

import com.team.user_admin_system.module.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    // 按分类查询（你前端要用到）
    List<Event> findByDynasty(String dynasty);
}