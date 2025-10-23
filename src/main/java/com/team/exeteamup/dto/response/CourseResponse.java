package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.CourseStatus;
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
    private Long courseId;
    private Long semesterId;
    private Long lecturerId;
    private String lecturerName;
    private String courseCode;
    private String courseName;
    private int maxGroup;
    private int groupCount;
    private CourseStatus status;
}
