package com.team.exeteamup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "student_groups")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = true)
    private Student leader;

    @Column(name = "group_name", nullable = true)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "member_count")
    private int memberCount;

    @Column(name = "group_status")
    private Boolean groupStatus;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> students;

//    @OneToMany(mappedBy = "group")
//    private List<GroupLecturer> groupLecturers;
}
