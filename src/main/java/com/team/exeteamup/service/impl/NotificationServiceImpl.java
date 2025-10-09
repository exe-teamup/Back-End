package com.team.exeteamup.service.impl;

import com.team.exeteamup.entity.Notification;
import com.team.exeteamup.repository.NotificationRepository;
import com.team.exeteamup.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(Notification notification) {
        return null;
    }

    @Override
    public List<Notification> getNotifications() {
        return List.of();
    }

    @Override
    public Notification getNotification(long id) {
        return null;
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return null;
    }

    @Override
    public Notification deleteNotification(long id) {
        return null;
    }
}
