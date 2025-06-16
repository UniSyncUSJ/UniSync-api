package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Common.EventCategory;
import com.uniSync.uniSync_api.Common.EventType;
import com.uniSync.uniSync_api.DTO.EventPatchRequest;
import com.uniSync.uniSync_api.Exceptions.ResourceNotFoundException;
import com.uniSync.uniSync_api.Model.Event;
import com.uniSync.uniSync_api.Repository.EventRepository;
import com.uniSync.uniSync_api.config.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.*;

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

    @Override
    public Event patchEventByAdmin(Long eventId, EventPatchRequest request, JwtUserDetails user) {

        Event event = null;
        try {
            event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

            // Validate ownership if admins are linked to specific entities
            if (!Objects.equals(event.getOrganizedBy(), user.getEntityId())) {
                throw new AccessDeniedException("You can't update this event");
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        // Apply updates if not null
        if (request.getEventName() != null) event.setEventName(request.getEventName());
        if (request.getDescription() != null) event.setDescription(request.getDescription());
        if (request.getVenue() != null) event.setVenue(request.getVenue());
        if (request.getEventType() != null) event.setEventType(request.getEventType());
        if (request.getEventCategory() != null) event.setEventCategory(request.getEventCategory());
        if (request.getEventDate() != null) event.setEventDate(request.getEventDate());
        if (request.getCoverImgURL() != null) event.setCoverImgURL(request.getCoverImgURL());
        if (request.getEventLink() != null) event.setEventLink(request.getEventLink());
        if (request.getAutoUnpublishDate() != null) event.setAutoUnpublishDate(request.getAutoUnpublishDate());
        if (request.getRegistrationDeadline() != null) event.setRegistrationDeadline(request.getRegistrationDeadline());
        if (request.getPublished() != null) event.setPublished(request.getPublished());

        return eventRepository.save(event);
    }

}

