package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.DuplicateObjectException;
import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;
import com.team.exeteamup.repository.NotificationRepository;
import com.team.exeteamup.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    @Override
    @Transactional
    public NotificationResponse saveNotification(NotificationRequest notificationRequest) {
        // check exists in DB
        notificationRepository.findByNotificationDetail(notificationRequest.getNotificationDetail())
                .ifPresent(notification1 -> {
                    throw new DuplicateObjectException("Notification already exists");
                }
        );
        // create new notification
        Notification notification = notificationRepository.save(new Notification(
                notificationRequest.getTitle(),
                notificationRequest.getNotificationDetail(),
                notificationRequest.getNotificationType())
        );
        // building response
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .notificationDetail(notification.getNotificationDetail())
                .notificationType(notification.getNotificationType())
                .title(notification.getTitle())
                .build();
    }

    @Override
    public List<NotificationResponse> getNotifications() {
        return notificationRepository.findAll().stream()// find all records in DB
                .map(notification -> NotificationResponse.builder() // map each record and build into response
                        .notificationId(notification.getNotificationId())
                        .title(notification.getTitle())
                        .notificationType(notification.getNotificationType())
                        .notificationDetail(notification.getNotificationDetail())
                        .build())
                .toList(); // return a list
    }

    @Override
    public NotificationResponse getNotification(long id) {
        // find in DB
        Notification notification = findNotificationById(id);
        // building response
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .notificationDetail(notification.getNotificationDetail())
                .notificationType(notification.getNotificationType())
                .title(notification.getTitle())
                .build();
    }

    @Override
    @Transactional
    public NotificationResponse updateNotification(long id, NotificationRequest notificationRequest) {
        // find in DB
        Notification notification = findNotificationById(id);
        // update notification
        notification.setNotificationDetail(notificationRequest.getNotificationDetail());
        notification.setNotificationType(notificationRequest.getNotificationType());
        notification.setTitle(notificationRequest.getTitle());
        Notification updatedNotification = notificationRepository.save(notification);
        // building response
        return NotificationResponse.builder()
                .notificationId(updatedNotification.getNotificationId())
                .notificationDetail(updatedNotification.getNotificationDetail())
                .notificationType(updatedNotification.getNotificationType())
                .title(updatedNotification.getTitle())
                .build();
    }

    @Override
    @Transactional
    public NotificationResponse deleteNotification(long id) {
        // find in DB
        Notification notification = findNotificationById(id);
        // delete notification
        notificationRepository.delete(notification);
        //building response
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .notificationDetail(notification.getNotificationDetail())
                .notificationType(notification.getNotificationType())
                .title(notification.getTitle())
                .build();
    }

    @Override
    public Notification findNotificationById(long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notification not found with id: " +
                                id)
                );
    }
}
