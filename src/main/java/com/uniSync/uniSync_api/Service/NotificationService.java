package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.EntityType;
import com.uniSync.uniSync_api.Model.Notification;
import java.util.List;

public interface NotificationService {
    void createEventNotification(Long eventId, String title, String message);
    void createEntityNotification(EntityType targetType, Long targetId, String title, String message);
    List<Notification> getUnreadNotificationsForStudent(Long studentId, int limit);
    void markNotificationAsRead(Long studentNotificationId);
}

