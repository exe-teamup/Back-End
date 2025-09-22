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
    private UUID courseId;
    private UUID semesterId;
    private UUID lecturerId;
    private String classCode;
    private int maxGroup;
    private int groupCount;
}
