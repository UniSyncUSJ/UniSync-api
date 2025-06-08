package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Model.EntityType;
import com.uniSync.uniSync_api.Model.Notification;
import com.uniSync.uniSync_api.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Create event notification
    @PostMapping("/event")
    public void createEventNotification(@RequestParam Long eventId,
                                        @RequestParam String title,
                                        @RequestParam String message) {
        notificationService.createEventNotification(eventId, title, message);
    }

    // Create entity notification
    @PostMapping("/entity")
    public void createEntityNotification(@RequestParam EntityType targetType,
                                         @RequestParam Long targetId,
                                         @RequestParam String title,
                                         @RequestParam String message) {
        notificationService.createEntityNotification(targetType, targetId, title, message);
    }

    // Get unread notifications for a student
    @GetMapping("/unread/{studentId}")
    public List<Notification> getUnreadNotifications(@PathVariable Long studentId,
                                                     @RequestParam(defaultValue = "10") int limit) {
        return notificationService.getUnreadNotificationsForStudent(studentId, limit);
    }

    // Mark a notification as read
    @PutMapping("/mark-read/{studentNotificationId}")
    public void markAsRead(@PathVariable Long studentNotificationId) {
        notificationService.markNotificationAsRead(studentNotificationId);
    }
}
