package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Common.EventCategory;
import com.uniSync.uniSync_api.Common.EventType;
import com.uniSync.uniSync_api.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Set<Event> findAllByEventCategory(EventCategory category);
    Set<Event> findAllByOrganizedBy(Long organizedBy);
    Set<Event> findAllByEventNameContaining(String keyword); // For search
    Set<Event> findAllByEventDateBetween(LocalDate start, LocalDate end);
    Set<Event> findAllByEventType(EventType eventType);

}
