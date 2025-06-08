package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;

@Entity
public class EventNotification extends Notification {

    @ManyToOne
    private Event event;

    // Getters and setters
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

