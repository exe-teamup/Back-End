package com.team.exeteamup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "majors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long majorId;

    @Column(name = "major_name", nullable = true, length = 50)
    private String majorName;

    @Column(name = "major_code", nullable = true, length = 20)
    private String majorCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_major_id", nullable = true)
    private Major parentMajor;

    @Column(name = "level")
    private Long level;
}
