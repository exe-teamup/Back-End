package com.team.exeteamup.entity;

import com.team.exeteamup.entity.embedded.AccountNotificationId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountNotification {
    @EmbeddedId
    private AccountNotificationId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("notificationId")
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_checked")
    private boolean isChecked;
}
