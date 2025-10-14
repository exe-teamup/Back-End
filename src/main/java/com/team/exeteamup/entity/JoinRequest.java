package com.team.exeteamup.entity;

import com.team.exeteamup.enums.joinRequest.JoinRequestStatus;
import com.team.exeteamup.enums.joinRequest.JoinRequestType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "join_requests")
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_request_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @NotNull
    private Group group;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    @NotNull
    private JoinRequestStatus requestStatus;

    @Column(name = "deny_reason", length = 100)
    private String denyReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    @NotNull
    private JoinRequestType requestType;

    public JoinRequest(User user,
                       Group group,
                       LocalDateTime createdAt,
                       JoinRequestStatus requestStatus,
                       String denyReason,
                       JoinRequestType requestType) {
        this.user = user;
        this.group = group;
        this.createdAt = createdAt;
        this.requestStatus = requestStatus;
        this.denyReason = denyReason;
        this.requestType = requestType;
    }
}
