package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Event;
import com.team.user_admin_system.module.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/list")
    public List<Event> getEvents(
            @RequestParam(required = false) String dynasty
    ) {
        // 没传分类 → 返回全部
        if (dynasty == null || dynasty.trim().isEmpty()) {
            return eventRepository.findAll();
        }

        // 按朝代筛选（你数据库里是 dynasty 字段）
        return eventRepository.findByDynasty(dynasty);
    }
}