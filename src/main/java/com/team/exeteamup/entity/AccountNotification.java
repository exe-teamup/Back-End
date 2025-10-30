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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_checked")
    private boolean isChecked;

    @Lob
    @Column(name = "formatted_content", columnDefinition = "TEXT")
    private String formattedContent;

}
