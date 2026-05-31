package com.team.user_admin_system.module.service.impl;

import org.springframework.stereotype.Service;
import com.team.user_admin_system.module.entity.Event;
import com.team.user_admin_system.module.repository.EventRepository;
import com.team.user_admin_system.module.service.EventService;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> listByCategory(String dynasty) {
        // 把 findByCategory 改成 findByDynasty
        return eventRepository.findByDynasty(dynasty);
    }
    @Override
    public Event getById(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        return optionalEvent.orElse(null);
    }
}