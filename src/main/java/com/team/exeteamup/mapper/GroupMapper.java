package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {

    public GroupResponse toResponse(Group group) {
        if (group == null) return null;

        List<Long> memberIds = group.getStudents() != null
                ? group.getStudents()
                .stream()
                .filter(student -> !student.isLeader())
                .map(Student::getStudentId)
                .toList()
                : List.of();

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .leaderId(group.getLeader() != null ? group.getLeader().getStudentId() : null)
                .memberIds(memberIds)
                .memberCount(group.getStudents() != null ? group.getStudents().size() : 0)
                .groupStatus(group.getGroupStatus())
                .build();
    }
}