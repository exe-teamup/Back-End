package com.team.exeteamup.service.inter.notification;

public interface NotificationBuilder {
    void saveFormattedNotification(long receiverAccountId, String templateCode, Object... args);
}
