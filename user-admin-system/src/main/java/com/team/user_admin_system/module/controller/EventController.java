package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Event;
import com.team.user_admin_system.module.repository.EventRepository;
import com.team.user_admin_system.module.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event") // 类上的统一前缀
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    // 事件列表接口：GET /api/event/list
    @GetMapping("/list")
    public List<Event> getEvents(
            @RequestParam(name = "dynasty", required = false) String dynasty
    ) {
        // 关键：当参数是 null 或者 "全部时期" 时，返回所有数据
        if (dynasty == null || "全部时期".equals(dynasty)) {
            return eventService.listAll();
        }
        return eventService.listByCategory(dynasty);
    }

    // 事件详情接口：GET /api/event/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }
}