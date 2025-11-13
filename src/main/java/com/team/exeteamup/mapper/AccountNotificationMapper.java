package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.AccountNotification;
import com.team.exeteamup.entity.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountNotificationMapper {

    public AccountNotificationResponse toResponse(AccountNotification accountNotification) {
        return AccountNotificationResponse.builder()
                .notificationId(accountNotification.getNotification().getId())
                .accountId(accountNotification.getAccount().getId())
                .accountNotificationId(accountNotification.getId())
                .isChecked(accountNotification.isChecked())
                .formattedContent(accountNotification.getFormattedContent())
                .createdAt(accountNotification.getCreatedAt())
                .build();
    }


    public AccountNotification toEntity(Account account, Notification notification, String formattedContent) {
        return AccountNotification.builder()
                .account(account)
                .notification(notification)
                .isChecked(false)
                .createdAt(LocalDateTime.now())
                .formattedContent(formattedContent)
                .build();
    }
}
