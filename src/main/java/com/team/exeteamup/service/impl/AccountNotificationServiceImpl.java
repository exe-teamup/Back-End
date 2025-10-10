package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.AccountNotificationRequest;
import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.AccountNotification;
import com.team.exeteamup.entity.Notification;
import com.team.exeteamup.repository.AccountNotificationRepository;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.NotificationRepository;
import com.team.exeteamup.service.AccountNotificationService;
import com.team.exeteamup.service.AccountService;
import com.team.exeteamup.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountNotificationServiceImpl implements AccountNotificationService {

    private final AccountNotificationRepository accountNotificationRepository;
    private final NotificationService notificationService;
    private final AccountService accountService;

    public AccountNotificationServiceImpl(AccountNotificationRepository accountNotificationRepository,
                                          NotificationService notificationService,
                                          AccountService accountService) {
        this.accountNotificationRepository = accountNotificationRepository;
        this.notificationService = notificationService;
        this.accountService = accountService;
    }

    @Override
    public List<AccountNotificationResponse> getAccountNotifications() {
        return accountNotificationRepository
                .findAll()
                .stream()
                .map(AccountNotification::buildResponse)
                .toList();
    }

    @Override
    public List<AccountNotificationResponse> getAccountNotificationsByAccountId(long accountId) {

        Account account = accountService.getAccountById(accountId);

        List<AccountNotification> accountNotifications = accountNotificationRepository.findByAccount(account);

        return accountNotifications
                .stream()
                .map(AccountNotification::buildResponse)
                .toList();
    }

    @Override
    @Transactional
    public List<AccountNotificationResponse> sendNotificationToAccounts(AccountNotificationRequest accountNotificationRequest) {

        List<Account> presentAccounts = accountService.presentAccounts(accountNotificationRequest.getAccountIds());
        Notification notification = notificationService.findNotificationById(accountNotificationRequest.getNotificationId());

        List<AccountNotification> saved = accountNotificationRepository.saveAll(
                presentAccounts.stream()
                        .map(account -> new AccountNotification(account, notification, LocalDateTime.now(), false))
                        .toList()
        );

        return saved
                .stream()
                .map(AccountNotification::buildResponse)
                .toList();

    }

    @Override
    @Transactional
    public List<AccountNotificationResponse> checkNotifications(List<Long> accountNotificationIds) {
        List<AccountNotification> accountNotifications = accountNotificationRepository.findAllById(accountNotificationIds);

        accountNotifications.forEach(an -> an.setChecked(true));

        List<AccountNotification> updated = accountNotificationRepository.saveAll(accountNotifications);

        return updated
                .stream()
                .map(AccountNotification::buildResponse)
                .toList();
    }

    @Override
    public AccountNotificationResponse getAccountNotificationResponseById(long accountNotificationId) {
        return getAccountNotificationById(accountNotificationId).buildResponse();
    }

    @Override
    @Transactional
    public AccountNotificationResponse checkNotification(long accountNotificationId) {

        AccountNotification accountNotification = getAccountNotificationById(accountNotificationId);

        accountNotification.setChecked(true);

        return accountNotificationRepository.save(accountNotification)
                .buildResponse();
    }

    @Override
    @Transactional
    public AccountNotificationResponse deleteAccountNotification(long accountNotificationId) {

        AccountNotification accountNotification = getAccountNotificationById(accountNotificationId);

        accountNotificationRepository.delete(accountNotification);

        return accountNotification.buildResponse();
    }

    @Override
    public AccountNotification getAccountNotificationById(long accountNotificationId) {
        return accountNotificationRepository.findById(accountNotificationId)
                .orElseThrow(() -> new EntityNotFoundException("Account Notification Not Found with id: " + accountNotificationId));
    }

}