package com.team.exeteamup.entity;

import com.team.exeteamup.entity.embedded.GroupLecturerId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_lecturer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupLecturer {

    @EmbeddedId
    private GroupLecturerId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @MapsId("lecturerId")
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @Column(name = "is_main")
    private boolean isMain;

}
