package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .templateCode(notification.getTemplateCode())
                .templateContent(notification.getTemplateContent())
                .notificationType(notification.getNotificationType())
                .build();
    }


    public Notification toEntity(NotificationRequest notificationRequest) {
        return Notification.builder()
                .templateCode(notificationRequest.getTemplateCode())
                .templateContent(notificationRequest.getTemplateContent())
                .notificationType(notificationRequest.getNotificationType())
                .build();
    }
}
