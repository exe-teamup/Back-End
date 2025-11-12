package com.team.exeteamup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.exeteamup.enums.GroupStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JoinRequest> joinRequests;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "group_template_id", referencedColumnName = "template_id")
    private GroupTemplate groupTemplate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)"
    )
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
