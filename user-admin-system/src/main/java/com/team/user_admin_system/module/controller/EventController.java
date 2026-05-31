package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Event;
import com.team.user_admin_system.module.service.EventService;
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
    private EventService eventService;

    @GetMapping("/list")
    public List<Event> getEvents(
            @RequestParam(required = false) String dynasty
    ) {
        // 关键：当参数是 null 或者 "全部时期" 时，返回所有数据
        if (dynasty == null || "全部时期".equals(dynasty)) {
            return eventService.listAll(); // 查全部
        }
        return eventService.listByCategory(dynasty); // 按朝代筛选
    }
}