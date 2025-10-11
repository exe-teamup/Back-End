package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.DuplicateObjectException;
import com.team.exeteamup.dto.request.NotificationRequest;
import com.team.exeteamup.dto.response.NotificationResponse;
import com.team.exeteamup.entity.Notification;
import com.team.exeteamup.mapper.NotificationMapper;
import com.team.exeteamup.repository.NotificationRepository;
import com.team.exeteamup.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {


    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


    @Override
    @Transactional
    public NotificationResponse saveNotification(NotificationRequest notificationRequest) {

        checkExist(notificationRequest);

        Notification notification = notificationRepository.save(new Notification(
                notificationRequest.getTitle(),
                notificationRequest.getNotificationDetail(),
                notificationRequest.getNotificationType())
        );

        return notificationMapper.toResponse(notification);
    }


    @Override
    public List<NotificationResponse> getNotifications() {
        return notificationRepository.findAll().stream()// find all records in DB
                .map(notificationMapper::toResponse)
                .toList(); // return a list
    }


    @Override
    public NotificationResponse findNotificationResponseById(long notificationId) {

        Notification notification = findNotificationById(notificationId);

        return notificationMapper.toResponse(notification);
    }


    @Override
    @Transactional
    public NotificationResponse updateNotification(long notificationId,
                                                   NotificationRequest notificationRequest) {

        Notification notification = findNotificationById(notificationId);

        notification.setNotificationDetail(notificationRequest.getNotificationDetail());
        notification.setNotificationType(notificationRequest.getNotificationType());
        notification.setTitle(notificationRequest.getTitle());

        return notificationMapper.toResponse(notificationRepository.save(notification));
    }


    @Override
    @Transactional
    public NotificationResponse deleteNotification(long notificationId) {

        Notification notification = findNotificationById(notificationId);

        notificationRepository.delete(notification);

        return notificationMapper.toResponse(notification);
    }


    @Override
    public Notification findNotificationById(long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notification not found with id: " +
                                notificationId)
                );
    }

    public void checkExist(NotificationRequest notificationRequest) {
         notificationRepository
                 .findByNotificationDetail(notificationRequest.getNotificationDetail())
                 .ifPresent(notification1 -> {
                             throw new DuplicateObjectException("Notification already exists");
                         }
                 );
    }
}
