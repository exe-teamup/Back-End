package com.team.exeteamup.entity.embedded;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AccountNotificationId implements Serializable {
    private long accountId;
    private long notificationId;
}
