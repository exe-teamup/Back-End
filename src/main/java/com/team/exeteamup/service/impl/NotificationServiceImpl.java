package com.team.exeteamup.service.impl;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.AccountNotification;
import com.team.exeteamup.exception.DuplicateObjectException;
import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;
import com.team.exeteamup.mapper.AccountNotificationMapper;
import com.team.exeteamup.mapper.NotificationMapper;
import com.team.exeteamup.repository.NotificationRepository;
import com.team.exeteamup.service.AccountNotificationService;
import com.team.exeteamup.service.AccountService;
import com.team.exeteamup.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.TargetSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AccountNotificationService accountNotificationService;
    private final AccountNotificationMapper accountNotificationMapper;
    private final AccountService accountService;
    private final NotificationMapper notificationMapper;


    @Value("${notification.template.member-kicked}")
    private String memberKickedTemplateCode;

    @Value("${notification.template.member-invited}")
    private String memberInvitedTemplateCode;


    @Override
    public void sendMemberKickedNotification(long kickedUserId, long groupId) {

    }


    @Override
    public void sendMemberInvitedNotification(long invitedUserId, long groupId) {

    }


    private void saveFormattedNotification(long accountId, String templateCode, Object... args) {

        Account account = accountService.getAccountById(accountId);

        Notification template = findByTemplateCode(templateCode);

        String formattedContent = String.format(template.getTemplateCode(), args);

        AccountNotification accountNotification = buildAccountNotification(account, template, formattedContent);

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


    @Override
    public Notification findById(long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Notification not found with id: " +
                                        notificationId)
                );
    }


    @Override
    public NotificationResponse findResponseById(long notificationId) {

        Notification notification = findById(notificationId);

        return notificationMapper.toResponse(notification);
    }


    @Override
    public List<NotificationResponse> findAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional
    public NotificationResponse saveNotification(NotificationRequest notificationRequest) {

        checkDuplicateTemplateCode(notificationRequest.getTemplateCode());

        Notification notification = notificationMapper.toEntity(notificationRequest);

        Notification savedNotification = notificationRepository.save(notification);

        return notificationMapper.toResponse(savedNotification);
    }


    private void checkDuplicateTemplateCode(String templateCode) {
        notificationRepository.findByTemplateCode(templateCode)
                .ifPresent(notification -> {
                    throw new DuplicateObjectException(
                            "Notification template code already exists: " +
                                    templateCode);
                });
    }


    @Override
    @Transactional
    public NotificationResponse updateNotification(long notificationId, NotificationRequest notificationRequest) {

        checkDuplicateTemplateCode(notificationRequest.getTemplateCode());

        Notification notification = findById(notificationId);

        notificationMapper.updateEntityFromRequest(notification, notificationRequest);

        Notification savedNotification = notificationRepository.save(notification);

        return notificationMapper.toResponse(savedNotification);
    }


    @Override
    @Transactional
    public NotificationResponse deleteNotification(long notificationId) {

        Notification notification = findById(notificationId);

        notificationRepository.delete(notification);

        return notificationMapper.toResponse(notification);
    }
}
