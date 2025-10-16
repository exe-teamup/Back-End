package com.team.exeteamup.entity;

import com.team.exeteamup.enums.GroupStatus;
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

    @Column(name = "group_name", nullable = true)
    private String groupName;

    @Column(name = "member_count")
    private int memberCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_status")
    private GroupStatus groupStatus;

    @OneToMany(mappedBy = "group")
    private List<User> users;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<JoinRequest> joinRequests;

    @OneToMany(mappedBy = "group")
    private List<GroupRegisterLecturer> lecturerSelections;

    @OneToMany(mappedBy = "group")
    private List<GroupLecturer> assignedLecturers;

    @OneToMany(mappedBy = "group")
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "official_lecturer_id")
    private Lecturer officialLecturer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

//    @OneToMany(mappedBy = "group")
//    private List<GroupLecturer> groupLecturers;
}
