package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {
    private long courseId;
    private long semesterId;
    private long lecturerId;
    private String classCode;
    private int maxGroup;
    private int groupCount;
}
