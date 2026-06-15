package com.team.user_admin_system.module.service;


import java.util.List;

import com.team.user_admin_system.module.entity.Event;

public interface EventService {
    List<Event> listAll();
    List<Event> listByCategory(String category);
    Event getById(Long id);
    Event saveEvent(Event event);
    void deleteEvent(Long id);
}