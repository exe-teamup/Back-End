package com.team.exeteamup.entity.embedded;

import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class GroupLecturerId implements Serializable {
    private long groupId;
    private long lecturerId;
}
