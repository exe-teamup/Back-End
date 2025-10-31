package com.team.exeteamup.listener.user;

import com.team.exeteamup.event.user.CreateGroupEvent;
import com.team.exeteamup.service.inter.notification.NotificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateGroupEventListener {

    private final NotificationBuilder notificationBuilder;

    @Value("${notification.template.user.create-group-success}")
    private String createGroupEventTemplateCode;

    @EventListener
    public void handleCreateGroupEvent(CreateGroupEvent createGroupEvent) {
        notificationBuilder.saveFormattedNotification(
                createGroupEvent.getReceiverAccountId(),
                createGroupEventTemplateCode,
                createGroupEvent.getGroupName());
    }
}
