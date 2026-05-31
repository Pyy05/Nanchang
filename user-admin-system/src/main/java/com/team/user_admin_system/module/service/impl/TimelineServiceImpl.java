package com.team.user_admin_system.module.service.impl;


import com.team.user_admin_system.entity.Timeline;
import com.team.user_admin_system.module.repository.TimelineRepository;
import com.team.user_admin_system.module.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineServiceImpl implements TimelineService {

    @Autowired
    private TimelineRepository timelineRepository;

    @Override
    public List<Timeline> getAllTimeline() {
        return timelineRepository.findAll();
    }
}