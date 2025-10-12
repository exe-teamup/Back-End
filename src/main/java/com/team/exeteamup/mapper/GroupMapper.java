package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    public GroupResponse toResponse(Group group) {
        if (group == null) return null;

        List<Long> memberIds = group.getStudents() != null
                ? group.getStudents()
                .stream()
                .filter(student -> !student.getIsLeader())
                .map(Student::getStudentId)
                .toList()
                : List.of();

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .memberIds(memberIds)
                .memberCount(group.getStudents() != null ? group.getStudents().size() : 0)
                .groupStatus(group.getGroupStatus())
                .build();
    }

    public List<GroupResponse> toResponseList(List<Group> groups) {
        return groups.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}