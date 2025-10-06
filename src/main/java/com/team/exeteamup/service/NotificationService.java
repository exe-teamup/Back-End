package com.team.exeteamup.service;

import com.team.exeteamup.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification saveNotification(Notification notification);
    List<Notification> getNotifications();
    Notification getNotification(long id);
    Notification updateNotification(Notification notification);
    Notification deleteNotification(long id);
}
