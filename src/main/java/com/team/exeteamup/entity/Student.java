package com.team.exeteamup.entity;

import com.team.exeteamup.enums.AccountRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;

    @Column(name = "student_code")
    private int studentCode;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "bio")
    private String bio;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "student_status")
    private boolean studentStatus;

    @Column(name = "is_leader", nullable = true)
    private boolean isLeader;
}
