package com.team.exeteamup.service.inter.notification;

import com.team.exeteamup.dto.request.AccountNotificationRequest;
import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.entity.AccountNotification;

import java.util.List;

public interface AccountNotificationService {
    List<AccountNotificationResponse> getAccountNotifications();
    List<AccountNotificationResponse> getAccountNotificationsByAccountId(long accountId); // remove parameter after config authorization
    List<AccountNotificationResponse> sendNotificationToAccounts(AccountNotificationRequest accountNotificationRequest);
    List<AccountNotificationResponse> checkNotifications(List<Long> accountNotificationIds);
    AccountNotificationResponse findResponseById(long accountNotificationId);
    AccountNotificationResponse checkNotification(long accountNotificationId);
    AccountNotificationResponse deleteAccountNotification(long accountNotificationId);
    AccountNotification findById(long accountNotificationId);
    void saveAccountNotification(AccountNotification accountNotification);
}
