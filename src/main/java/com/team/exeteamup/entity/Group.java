package com.team.exeteamup.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "student_groups")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Group {
    @Id
    @GeneratedValue(generator = "uuid-v7")
    @GenericGenerator(name = "uuid-v7", strategy = "com.team.exeteamup.util.UUIDv7Generator")
    private UUID groupId;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = true)
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "member_count", nullable = false)
    private int memberCount;

    @Column(name = "group_status", nullable = false)
    private boolean groupStatus;
}
