package com.team.exeteamup.entity;

import com.team.exeteamup.entity.embedded.GroupLecturerId;
import com.team.exeteamup.enums.LecturerStatus;
import com.team.exeteamup.enums.RegisterStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_register_lecturer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRegisterLecturer {
    @EmbeddedId
    private GroupLecturerId groupLecturerId;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @MapsId("lecturerId")
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @Column(name = "register_order")
    private int registerOrder;

    @Enumerated(EnumType.STRING)
    private RegisterStatus registerStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
