package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.*;
import com.uniSync.uniSync_api.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EventNotificationRepository eventNotificationRepository;

    @Autowired
    private EntityNotificationRepository entityNotificationRepository;

    @Autowired
    private StudentNotificationRepository studentNotificationRepository;

    @Override
    public void createEventNotification(Long eventId, String title, String message) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventNotification notification = new EventNotification();
        notification.setEvent(event);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());

        List<Student> students = new ArrayList<>(studentRepository.findByEvents_Id(eventId));
        List<StudentNotification> studentNotifications = new ArrayList<>();

        for (Student student : students) {
            StudentNotification sn = new StudentNotification();
            sn.setStudent(student);
            sn.setNotification(notification);
            studentNotifications.add(sn);
        }

        notification.setRecipients(studentNotifications);
        eventNotificationRepository.save(notification);
    }

    @Override
    public void createEntityNotification(EntityType targetType, Long targetId, String title, String message) {
        EntityNotification notification = new EntityNotification();
        notification.setTargetType(targetType);
        notification.setTargetId(targetId);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());

        List<Student> students = switch (targetType) {
            case FACULTY -> new ArrayList<>(studentRepository.findByFacultyId(targetId));
            case DEPARTMENT -> new ArrayList<>(studentRepository.findByDepartments_Id(targetId));
            case SOCIETY -> new ArrayList<>(studentRepository.findBySocieties_Id(targetId));
        };

        List<StudentNotification> studentNotifications = new ArrayList<>();

        for (Student student : students) {
            StudentNotification sn = new StudentNotification();
            sn.setStudent(student);
            sn.setNotification(notification);
            studentNotifications.add(sn);
        }

        notification.setRecipients(studentNotifications);
        entityNotificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnreadNotificationsForStudent(Long studentId, int limit) {
        return studentNotificationRepository
                .findTop10ByStudentIdAndIsReadFalseOrderByNotification_TimestampDesc(studentId)
                .stream()
                .map(StudentNotification::getNotification)
                .limit(limit)
                .toList();
    }

    @Override
    public void markNotificationAsRead(Long studentNotificationId) {
        StudentNotification sn = studentNotificationRepository.findById(studentNotificationId)
                .orElseThrow(() -> new RuntimeException("StudentNotification not found"));
        sn.setRead(true);
        studentNotificationRepository.save(sn);
    }
}

