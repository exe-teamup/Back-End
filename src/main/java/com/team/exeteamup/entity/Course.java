package com.team.exeteamup.entity;

import com.team.exeteamup.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private long courseId;

    @ManyToOne
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id")
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id", referencedColumnName = "lecturer_id", nullable = true)
    private Lecturer lecturer;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "max_group", nullable = true)
    private int maxGroup;

    @Column(name = "group_count", nullable = true)
    private int groupCount;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Group> groups;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<User> users;

    @Column(name = "course_status")
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
}
