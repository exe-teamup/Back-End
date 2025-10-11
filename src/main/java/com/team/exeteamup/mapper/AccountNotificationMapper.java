package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.entity.AccountNotification;
import org.springframework.stereotype.Component;

@Component
public class AccountNotificationMapper {

    public AccountNotificationResponse toResponse(AccountNotification accountNotification) {
        return AccountNotificationResponse.builder()
                .notificationId(accountNotification.getNotification().getNotificationId())
                .accountId(accountNotification.getAccount().getAccountId())
                .accountNotificationId(accountNotification.getId())
                .isChecked(accountNotification.isChecked())
                .build();
    }
}
