package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByNotificationDetail(String notificationDetail);
}
