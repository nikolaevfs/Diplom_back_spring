package com.vkr.studentsapp.service;

import com.vkr.studentsapp.model.Event;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findGroupEvents(Group group){
        return this.eventRepository.findEventsByGroup(group);
    }

    public void addEvent(Event event){
        this.eventRepository.save(event);
    }
}
