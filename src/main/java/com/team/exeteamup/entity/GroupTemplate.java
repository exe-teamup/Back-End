    package com.team.exeteamup.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Entity
    @Table(name = "group_templates")
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public class GroupTemplate {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "template_id")
        private Long id;

        @Column(name = "min_member", nullable = false)
        private Integer minMember;

        @Column(name = "max_member", nullable = false)
        private Integer maxMember;

        @Column(name = "min_major", nullable = false)
        private Integer minMajor;

        @Column(name = "template", nullable = false, length = 255)
        private String template;

        @ManyToMany(mappedBy = "groupTemplates")
        private List<Semester> semesters;

        @OneToMany(mappedBy = "groupTemplate")
        private List<Group> groups;
    }
