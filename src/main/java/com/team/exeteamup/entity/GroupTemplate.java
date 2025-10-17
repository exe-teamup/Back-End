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
    private Long templateId;

    @Column(name = "min_member", nullable = false)
    private int min_member;

    @Column(name = "max_member", nullable = false)
    private int max_member;

    @Column(name = "min_major", nullable = false)
    private int min_major;

    @Column(name = "template", nullable = false, length = 255)
    private String template;

    @ManyToMany(mappedBy = "groupTemplates")
    private List<Semester> semesters;

}
