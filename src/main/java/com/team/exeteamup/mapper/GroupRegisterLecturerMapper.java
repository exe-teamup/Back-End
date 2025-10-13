package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.LecturerSelectionResponse;
import com.team.exeteamup.entity.GroupRegisterLecturer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupRegisterLecturerMapper {
    public LecturerSelectionResponse toResponse(Long groupId, List<GroupRegisterLecturer> entities) {
        List<LecturerSelectionResponse.LecturerItem> lecturerItems = entities.stream()
                .map(entity -> LecturerSelectionResponse.LecturerItem.builder()
                        .lecturerId(entity.getLecturer().getLecturerId())
                        .lecturerName(entity.getLecturer().getFullName())
                        .lecturerEmail(entity.getLecturer().getAccount().getEmail())
                        .registerOrder(entity.getRegisterOrder())
                        .createdAt(entity.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return LecturerSelectionResponse.builder()
                .groupId(groupId)
                .selectedLecturers(lecturerItems)
                .build();
    }
}
