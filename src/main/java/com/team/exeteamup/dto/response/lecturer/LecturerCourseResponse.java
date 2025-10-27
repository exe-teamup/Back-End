package com.team.exeteamup.dto.response.lecturer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LecturerCourseResponse {
    private String courseCode;
    private Long courseId;
}
