package com.team.exeteamup.entity;

import com.team.exeteamup.enums.account.AccountRole;
import com.team.exeteamup.enums.account.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_role", nullable = false)
    private AccountRole role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account")
    private List<AccountNotification> accountNotifications;

    @OneToOne(mappedBy = "account")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

}
