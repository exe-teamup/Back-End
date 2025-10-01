package com.team.exeteamup.entity.embedded;

import jakarta.persistence.Embeddable;

@Embeddable
public class AccountNotificationId {
    private long accountId;
    private long notificationId;
}
