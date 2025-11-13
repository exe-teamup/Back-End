package com.team.exeteamup.service.impl.notification;

import com.team.exeteamup.exception.DuplicateObjectException;
import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;
import com.team.exeteamup.mapper.AccountNotificationMapper;
import com.team.exeteamup.mapper.NotificationMapper;
import com.team.exeteamup.repository.NotificationRepository;
import com.team.exeteamup.service.inter.notification.AccountNotificationService;
import com.team.exeteamup.service.inter.AccountService;
import com.team.exeteamup.service.inter.notification.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


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
