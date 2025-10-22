package com.team.exeteamup.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class GroupLecturerId implements Serializable {
    private Long groupId;
    private Long lecturerId;

}
