package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.AccountNotificationRequest;
import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.entity.AccountNotification;

import java.util.List;

public interface AccountNotificationService {
    List<AccountNotificationResponse> getAccountNotifications();
    List<AccountNotificationResponse> getAccountNotificationsByAccountId(long accountId); // remove parameter after config authorization
    List<AccountNotificationResponse> notifyToAccounts(AccountNotificationRequest accountNotificationRequest);
    List<AccountNotificationResponse> checkNotifications(List<Long> accountNotificationIds);
    AccountNotificationResponse checkNotification(long accountNotificationId);
    AccountNotificationResponse deleteAccountNotification(long accountNotificationId);
}
