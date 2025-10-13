package com.team.exeteamup.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.exeteamup.enums.SemesterStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "semesters")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semester_id")
    private Long semesterId;

    @ManyToMany
    @JoinTable(
            name = "semester_group_template",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    private List<GroupTemplate> groupTemplates;

    @Column(name = "semester_code", nullable = true)
    private String semesterCode;

    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_status")
    private SemesterStatus semesterStatus;

    @OneToMany(mappedBy = "semester")
    private List<Course> courses;
}