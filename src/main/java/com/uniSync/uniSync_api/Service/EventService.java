package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Common.EventCategory;
import com.uniSync.uniSync_api.Common.EventType;
import com.uniSync.uniSync_api.Model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {
    Event createEvent(Event event);
    Optional<Event> getEventById(Long id);
    List<Event> getAllEvents();
    void deleteEventById(Long id);
    List<Event> getEventsByEventCategory(EventCategory category);
    List<Event> getEventsByOrganizerId(Long organizerId);
    List<Event> getEventsContainingKeyword(String keyword);
    List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end);
    List<Event> getEventsByType(EventType eventType);
}
