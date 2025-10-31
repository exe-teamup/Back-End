package com.team.exeteamup.service.impl.notification;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.AccountNotification;
import com.team.exeteamup.entity.Notification;
import com.team.exeteamup.mapper.AccountNotificationMapper;
import com.team.exeteamup.repository.NotificationRepository;
import com.team.exeteamup.service.inter.notification.AccountNotificationService;
import com.team.exeteamup.service.inter.AccountService;
import com.team.exeteamup.service.inter.notification.NotificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationBuilderImpl implements NotificationBuilder {

    private final AccountService accountService;
    private final AccountNotificationService accountNotificationService;
    private final NotificationRepository notificationRepository;
    private final AccountNotificationMapper accountNotificationMapper;


    @Override
    @Transactional
    public void saveFormattedNotification(long receiverAccountId, String templateCode, Object... args) {

        Account receiverAccount = accountService.getAccountById(receiverAccountId);

        Notification template = findByTemplateCode(templateCode);

        String formattedContent = String.format(template.getTemplateContent(), args);

        AccountNotification accountNotification = buildAccountNotification(receiverAccount, template, formattedContent);

        accountNotificationService.saveAccountNotification(accountNotification);
    }


    private Notification findByTemplateCode(String templateCode) {
        return notificationRepository.findByTemplateCode(templateCode)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Notification template not found: " +
                                        templateCode)
                );
    }


    private AccountNotification buildAccountNotification(Account account,
                                                         Notification notification,
                                                         String formattedContent) {
        return accountNotificationMapper.toEntity(account, notification, formattedContent);
    }
}
