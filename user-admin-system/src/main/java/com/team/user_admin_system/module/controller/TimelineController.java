package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.entity.Timeline;
import com.team.user_admin_system.module.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    @GetMapping("/timeline")
    public List<Timeline> getTimeline() {
        return timelineService.getAllTimeline();
    }
}