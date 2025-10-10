package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;

import java.util.List;

public interface NotificationService {
    NotificationResponse saveNotification(NotificationRequest notificationRequest);
    List<NotificationResponse> getNotifications();
    NotificationResponse getNotification(long id);
    NotificationResponse updateNotification(long id, NotificationRequest notificationRequest);
    NotificationResponse deleteNotification(long id);
    Notification findNotificationById(long id);
}
