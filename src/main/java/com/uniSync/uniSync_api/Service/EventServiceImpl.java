package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Common.EventCategory;
import com.uniSync.uniSync_api.Common.EventType;
import com.uniSync.uniSync_api.Model.Event;
import com.uniSync.uniSync_api.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end) {
        Set<Event> events = eventRepository.findAllByEventDateBetween(start,end);
        return new ArrayList<>(events);
    }

    @Override
    public List<Event> getEventsByOrganizerId(Long organizerId) {
        Set<Event> events = eventRepository.findAllByOrganizedBy(organizerId);
        return new ArrayList<>(events);
    }

    @Override
    public List<Event> getEventsByEventCategory(EventCategory category) {
        Set<Event> events = eventRepository.findAllByEventCategory(category);
        return new ArrayList<>(events);
    }

    @Override
    public List<Event> getEventsByType(EventType eventType) {
        Set<Event> events = eventRepository.findAllByEventType(eventType);
        return new ArrayList<>(events);
    }

    @Override
    public List<Event> getEventsContainingKeyword(String keyword) {
        Set<Event> events = eventRepository.findAllByEventNameContaining(keyword);
        return new ArrayList<>(events);
    }
}

