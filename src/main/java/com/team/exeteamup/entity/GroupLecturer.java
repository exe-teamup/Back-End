package com.team.exeteamup.entity;

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
@IdClass(GroupLecturerId.class)
public class GroupLecturer {
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;

    @Id
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturerId;

}
