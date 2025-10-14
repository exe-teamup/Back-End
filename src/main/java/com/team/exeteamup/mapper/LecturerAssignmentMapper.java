package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.AssignLecturerResponse;
import com.team.exeteamup.entity.GroupLecturer;
import org.springframework.stereotype.Component;

@Component
public class LecturerAssignmentMapper {
    public AssignLecturerResponse toResponse(GroupLecturer groupLecturer) {
        return AssignLecturerResponse.builder()
                .groupId(groupLecturer.getGroup().getGroupId())
                .lecturerId(groupLecturer.getLecturer().getLecturerId())
                .LecturerName(groupLecturer.getLecturer().getFullName())
                .lecturerStatus(groupLecturer.getLecturer().getLecturerStatus())
                .isMain(groupLecturer.isMain())
                .assignedAt(groupLecturer.getAssignedAt())
                .build();
    }
}
