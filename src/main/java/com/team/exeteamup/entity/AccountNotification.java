package com.team.exeteamup.entity;

import com.team.exeteamup.dto.response.AccountNotificationResponse;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_notification_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_checked")
    private boolean isChecked;

    public AccountNotification(Account account,
                               Notification notification,
                               LocalDateTime createdAt,
                               boolean isChecked) {
        this.account = account;
        this.notification = notification;
        this.createdAt = createdAt;
        this.isChecked = isChecked;
    }
}
