package com.team.exeteamup.service.inter.notification;

import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification findById(long notificationId);
    NotificationResponse findResponseById(long notificationId);
    List<NotificationResponse> findAllNotifications();
    NotificationResponse saveNotification(NotificationRequest notificationRequest);
    NotificationResponse updateNotification(long notificationId, NotificationRequest notificationRequest);
    NotificationResponse deleteNotification(long notificationId);
}
