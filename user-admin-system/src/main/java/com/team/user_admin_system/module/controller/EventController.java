package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Event;
import com.team.user_admin_system.module.repository.EventRepository;
import com.team.user_admin_system.module.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 简化事件列表接口：GET /api/event/simple-list
    // 返回只包含核心字段(id, name, dynasty)的事件列表，用于下拉选择等场景
    @GetMapping("/simple-list")
    public List<Map<String, Object>> getSimpleEventList() {
        // 获取所有事件
        List<Event> events = eventService.listAll();
        // 将事件对象转换为简化的 Map 结构，只保留关键信息
        return events.stream().map(event -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", event.getEventId());      // 事件ID
            map.put("name", event.getEventName());  // 事件名称
            map.put("dynasty", event.getDynasty()); // 所属朝代
            return map;
        }).collect(Collectors.toList());
    }
}