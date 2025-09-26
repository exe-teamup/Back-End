package com.team.exeteamup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupLecturerId implements Serializable {
    private long groupId;
    private long lecturerId;
}
