package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Common.EventCategory;
import com.uniSync.uniSync_api.Common.EventType;
import com.uniSync.uniSync_api.DTO.EventPatchRequest;
import com.uniSync.uniSync_api.Model.Event;
import com.uniSync.uniSync_api.Service.EventService;
import com.uniSync.uniSync_api.config.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/between")
    public ResponseEntity<List<Event>> getEventsBetween(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(eventService.getEventsBetween(start, end));
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<Event>> getEventsByOrganizerId(@PathVariable Long organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizerId(organizerId));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Event>> getEventsByCategory(@RequestParam("category") EventCategory category) {
        return ResponseEntity.ok(eventService.getEventsByEventCategory(category));
    }

    @GetMapping("/type")
    public ResponseEntity<List<Event>> getEventsByType(@RequestParam("type") EventType eventType) {
        return ResponseEntity.ok(eventService.getEventsByType(eventType));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Event>> getEventsByKeyword(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(eventService.getEventsContainingKeyword(keyword));
    }

    @PatchMapping("/admin/{eventId}")
    public ResponseEntity<Event> patchEventByAdmin(
            @PathVariable Long eventId,
            @RequestBody EventPatchRequest request,
            @AuthenticationPrincipal JwtUserDetails user
    ) {
        return ResponseEntity.ok(eventService.patchEventByAdmin(eventId, request, user));
    }
}
