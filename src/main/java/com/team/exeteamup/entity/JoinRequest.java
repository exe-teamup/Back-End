package com.team.exeteamup.entity;

import com.team.exeteamup.enums.JoinRequestStatus;
import com.team.exeteamup.enums.JoinRequestType;
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
    @JoinColumn(name = "student_id")
    @NotNull
    private Student student;

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

    public JoinRequest(Student student,
                       Group group,
                       LocalDateTime createdAt,
                       JoinRequestStatus requestStatus,
                       String denyReason,
                       JoinRequestType requestType) {
        this.student = student;
        this.group = group;
        this.createdAt = createdAt;
        this.requestStatus = requestStatus;
        this.denyReason = denyReason;
        this.requestType = requestType;
    }
}
