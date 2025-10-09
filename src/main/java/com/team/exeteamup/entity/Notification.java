package com.team.exeteamup.entity;

import com.team.exeteamup.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "notification_detail", nullable = false, columnDefinition = "TEXT")
    @Lob
    private String notificationDetail;


    @Column(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @OneToMany(mappedBy = "notification")
    private List<AccountNotification> accountNotifications;

    public Notification(String title, String notificationDetail, NotificationType notificationType) {
        this.title = title;
        this.notificationDetail = notificationDetail;
        this.notificationType = notificationType;
    }
}
