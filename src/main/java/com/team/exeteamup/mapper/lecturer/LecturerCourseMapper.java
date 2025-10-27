package com.team.exeteamup.mapper.lecturer;

import com.team.exeteamup.dto.response.lecturer.LecturerCourseResponse;
import com.team.exeteamup.entity.Lecturer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LecturerCourseMapper {
    List<LecturerCourseResponse> toResponseList(Lecturer lecturer) {
        return lecturer.getCourses().stream()
                .map(course -> LecturerCourseResponse.builder()
                        .courseId(course.getCourseId())
                        .courseCode(course.getCourseCode())
                        .build())
                .toList();
    }
}
