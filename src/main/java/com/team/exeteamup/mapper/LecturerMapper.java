package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.LecturerResponse;
import com.team.exeteamup.entity.Lecturer;
import org.springframework.stereotype.Component;

@Component
public class LecturerMapper {
    public LecturerResponse toResponse(Lecturer lecturer) {
        if (lecturer == null) {
            return null;
        }

        return LecturerResponse.builder()
                .lecturerId(lecturer.getLecturerId())
                .lecturerName(lecturer.getFullName())
                .lecturerStatus(lecturer.getLecturerStatus().name())
                .accountId(lecturer.getAccount().getAccountId())
                .accountStatus(lecturer.getAccount().getStatus().name())
                .build();
    }
}
