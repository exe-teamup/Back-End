package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.AutoAssignLecturerRequest;
import com.team.exeteamup.dto.response.AutoAssignGroupResponse;
import com.team.exeteamup.dto.response.AutoAssignLecturerResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Lecturer;
import org.springframework.stereotype.Component;

@Component
public class AutoAssignLecturerMapper {
    public AutoAssignGroupResponse toResponse(Group group, Lecturer lecturer, int registerOrder, boolean assigned, String message) {
        return AutoAssignGroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .lecturerName(lecturer != null ? lecturer.getFullName() : null)
                .registerOrder(registerOrder)
                .assigned(assigned)
                .message(message)
                .build();
    }
}
