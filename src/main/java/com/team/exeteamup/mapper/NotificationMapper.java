package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .notificationDetail(notification.getNotificationDetail())
                .notificationType(notification.getNotificationType())
                .title(notification.getTitle())
                .build();
    }
}
