package com.team.exeteamup.service.impl.notification;

import com.team.exeteamup.dto.request.AccountNotificationRequest;
import com.team.exeteamup.dto.response.AccountNotificationResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.AccountNotification;
import com.team.exeteamup.mapper.AccountNotificationMapper;
import com.team.exeteamup.repository.AccountNotificationRepository;
import com.team.exeteamup.service.inter.notification.AccountNotificationService;
import com.team.exeteamup.service.inter.AccountService;
import com.team.exeteamup.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountNotificationServiceImpl implements AccountNotificationService {


    private final AccountNotificationRepository accountNotificationRepository;
    private final AccountService accountService;
    private final AccountNotificationMapper accountNotificationMapper;
    private final UserUtils userUtils;


    @Override
    public List<AccountNotificationResponse> getAccountNotifications() {
        return accountNotificationRepository
                .findAll()
                .stream()
                .map(accountNotificationMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public List<AccountNotificationResponse> getMyNotification() {

        Account account = userUtils.getCurrentAccount();

        List<AccountNotification> accountNotifications =
                accountNotificationRepository.findByAccount(account);

        return accountNotifications
                .stream()
                .map(accountNotificationMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    @Transactional
    public List<AccountNotificationResponse> checkNotifications(List<Long> accountNotificationIds) {
        List<AccountNotification> accountNotifications =
                accountNotificationRepository.findAllById(accountNotificationIds);

        accountNotifications.forEach(an -> an.setChecked(true));

        List<AccountNotification> updated =
                accountNotificationRepository.saveAll(accountNotifications);

        return updated
                .stream()
                .map(accountNotificationMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public AccountNotificationResponse findResponseById(
            long accountNotificationId) {
        return accountNotificationMapper
                .toResponse(findById(accountNotificationId));
    }


    @Override
    @Transactional
    public AccountNotificationResponse checkNotification(long accountNotificationId) {

        AccountNotification accountNotification =
                findById(accountNotificationId);

        accountNotification.setChecked(true);

        return accountNotificationMapper
                .toResponse(accountNotificationRepository.save(accountNotification));
    }


    @Override
    @Transactional
    public AccountNotificationResponse deleteAccountNotification(long accountNotificationId) {

        AccountNotification accountNotification =
                findById(accountNotificationId);

        accountNotificationRepository.delete(accountNotification);

        return accountNotificationMapper.toResponse(accountNotification);
    }


    @Override
    public AccountNotification findById(long accountNotificationId) {
        return accountNotificationRepository.findById(accountNotificationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Account Notification Not Found with id: " +
                                accountNotificationId)
                );
    }

    @Override
    public void saveAccountNotification(AccountNotification accountNotification) {
        accountNotificationRepository.save(accountNotification);
    }

}