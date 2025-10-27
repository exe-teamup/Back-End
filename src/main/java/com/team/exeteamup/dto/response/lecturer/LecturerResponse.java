package com.team.exeteamup.dto.response.lecturer;

import java.util.List;

import com.team.exeteamup.dto.response.CourseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LecturerResponse {
    private long lecturerId;
    private String lecturerName;
    private String lecturerStatus;
    private String accountStatus;
    private long accountId;
    private String email;
    private List<LecturerCourseResponse> courses;
}
