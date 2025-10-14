package com.team.exeteamup.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GroupLecturerId implements Serializable {
    private long groupId;
    private long lecturerId;

}
