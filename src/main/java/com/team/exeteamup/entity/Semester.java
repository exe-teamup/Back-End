package com.team.exeteamup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "semester")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Semester {
    @Id
    @GeneratedValue(generator = "uuid-v7")
    @GenericGenerator(name = "uuid-v7", strategy = "com.team.exeteamup.util.UUIDv7Generator")
    private UUID semesterId;

    @ManyToOne
    @JoinColumn(name = "group_constraint_id")
    private Group groupConstraint;

    @Column(name = "semester_code", nullable = false)
    private String semesterCode;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "semester_status", nullable = false)
    private String semesterStatus;
}