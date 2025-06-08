package com.uniSync.uniSync_api.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private List<StudentNotification> recipients;

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<StudentNotification> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<StudentNotification> recipients) {
        this.recipients = recipients;
    }

    public Notification(String title, String message, LocalDateTime timestamp, List<StudentNotification> recipients) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.recipients = recipients;
    }

    public Long getId() {
        return id;
    }

    public Notification() { }
}

