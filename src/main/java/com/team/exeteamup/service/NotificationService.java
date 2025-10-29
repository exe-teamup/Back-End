package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;

import java.util.List;

public interface NotificationService {
    void sendMemberKickedNotification(long kickedUserId, long groupId);
    void sendMemberInvitedNotification(long invitedUserId, long groupId);
    Notification findById(long notificationId);
    NotificationResponse findResponseById(long notificationId);
    List<NotificationResponse> findAllNotifications();
    NotificationResponse saveNotification(NotificationRequest notificationRequest);
    NotificationResponse updateNotification(long notificationId, NotificationRequest notificationRequest);
    NotificationResponse deleteNotification(long notificationId);
}
