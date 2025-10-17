package com.team.exeteamup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseUpdateRequest {
    private Long courseId;
    private Long semesterId;
    private Long lecturerId;
    private String courseCode;
    private String courseName;
    private int maxGroup;
    private int groupCount;
}
