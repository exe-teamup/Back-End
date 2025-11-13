package com.team.exeteamup.listener.user;

import com.team.exeteamup.event.user.CreateGroupEvent;
import com.team.exeteamup.event.user.UserGroupEvent;
import com.team.exeteamup.service.inter.notification.NotificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGroupEventListener {

    private final NotificationBuilder notificationBuilder;

    @Value("${notification.template.user.join-group}")
    private String joinGroupEventTemplateCode;

    @Value("${notification.template.user.removed-from-group}")
    private String removedFromGroupEventTemplateCode;

    @Value("${notification.template.user.leave-group}")
    private String leaveGroupEventTemplateCode;


    @EventListener(condition = "#userGroupEvent.eventType.name() == 'JOIN_GROUP'")
    public void handleUserJoinGroupEvent(UserGroupEvent userGroupEvent) {
        notificationBuilder.saveFormattedNotification(
                userGroupEvent.getReceiverAccountId(),
                joinGroupEventTemplateCode,
                userGroupEvent.getGroupName());
    }


    @EventListener(condition = "#userGroupEvent.eventType.name() == 'REMOVED_FROM_GROUP'")
    public void handleUserRemovedFromGroupEvent(UserGroupEvent userGroupEvent) {
        notificationBuilder.saveFormattedNotification(
                userGroupEvent.getReceiverAccountId(),
                removedFromGroupEventTemplateCode,
                userGroupEvent.getGroupName());
    }


    @EventListener(condition = "#userGroupEvent.eventType.name() == 'LEAVE_GROUP'")
    public void handleUserLeaveGroupEvent(UserGroupEvent userGroupEvent) {
        notificationBuilder.saveFormattedNotification(
                userGroupEvent.getReceiverAccountId(),
                leaveGroupEventTemplateCode,
                userGroupEvent.getGroupName());
    }
}
