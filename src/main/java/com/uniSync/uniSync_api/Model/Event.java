package com.uniSync.uniSync_api.Model;

import com.uniSync.uniSync_api.Common.EventCategory;
import com.uniSync.uniSync_api.Common.EventType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private String description;
    private String venue;
    private EventType eventType;
    private EventCategory eventCategory;
    private LocalDateTime eventDate;
    private String coverImgURL;
    private String eventLink;
    private Long organizedBy;
    private boolean published = false;
    private LocalDateTime autoUnpublishDate;
    private LocalDateTime registrationDeadline;
    private int participantCount = 0;
    private int likeCount = 0;

    @ManyToMany(mappedBy = "events")
    private Set<Student> students = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getCoverImgURL() {
        return coverImgURL;
    }

    public void setCoverImgURL(String coverImgURL) {
        this.coverImgURL = coverImgURL;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public Long getOrganizedBy() {
        return organizedBy;
    }

    public void setOrganizedBy(Long organizedBy) {
        this.organizedBy = organizedBy;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public LocalDateTime getAutoUnpublishDate() {
        return autoUnpublishDate;
    }

    public void setAutoUnpublishDate(LocalDateTime autoUnpublishDate) {
        this.autoUnpublishDate = autoUnpublishDate;
    }

    public LocalDateTime getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    // Constructors
    public Event(String eventName, String description, String venue, EventType eventType, EventCategory eventCategory,
                 LocalDateTime eventDate, String coverImgURL, String eventLink, Long organizedBy, LocalDateTime autoUnpublishDate,
                 LocalDateTime registrationDeadline) {
        setEventName(eventName);
        setDescription(description);
        setVenue(venue);
        setEventType(eventType);
        setEventCategory(eventCategory);
        setEventDate(eventDate);
        setCoverImgURL(coverImgURL);
        setEventLink(eventLink);
        setOrganizedBy(organizedBy);
        setAutoUnpublishDate(autoUnpublishDate);
        setRegistrationDeadline(registrationDeadline);
    }

    public Event() { }


}
