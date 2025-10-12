package com.team.exeteamup.entity;

import com.team.exeteamup.entity.embedded.PostMajorId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_major")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMajor {

    @EmbeddedId
    private PostMajorId id;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @MapsId("majorId")
    @JoinColumn(name = "major_id")
    private Major major;

    @Column(name = "student_num")
    private Integer studentNum;
}
