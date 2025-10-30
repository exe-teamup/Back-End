package com.team.exeteamup.mapper.lecturer;

import com.team.exeteamup.dto.response.lecturer.LecturerResponse;
import com.team.exeteamup.entity.Lecturer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LecturerMapper {

    private final LecturerCourseMapper lecturerCourseMapper;

    public LecturerResponse toResponse(Lecturer lecturer) {
        if (lecturer == null) {
            return null;
        }
        return LecturerResponse.builder()
                .lecturerId(lecturer.getLecturerId())
                .lecturerName(lecturer.getFullName())
                .lecturerStatus(lecturer.getLecturerStatus().name())
                .accountId(lecturer.getAccount().getId())
                .accountStatus(lecturer.getAccount().getStatus().name())
                .email(lecturer.getAccount().getEmail())
                .courses(lecturerCourseMapper.toResponseList(lecturer))
                .build();
    }
}
