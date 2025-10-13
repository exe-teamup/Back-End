package com.team.exeteamup.entity;

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

    @Column(name = "max_group")
    private int maxGroup;

    @Column(name = "group_count")
    private int groupCount;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups;
}
