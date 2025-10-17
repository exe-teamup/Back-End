package com.team.exeteamup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_major")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_major_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @Column(name = "student_num")
    private Integer studentNum;
}
