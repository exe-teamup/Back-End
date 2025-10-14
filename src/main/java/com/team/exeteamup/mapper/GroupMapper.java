package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    public GroupResponse toResponse(Group group) {
        if (group == null) return null;

        List<Long> memberIds = group.getUsers() != null
                ? group.getUsers()
                .stream()
                .filter(user -> !user.getIsLeader())
                .map(User::getUserId)
                .toList()
                : List.of();

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .memberIds(memberIds)
                .memberCount(group.getUsers() != null ? group.getUsers().size() : 0)
                .courseId(group.getCourse().getCourseId())
                .groupStatus(group.getGroupStatus())
                .build();
    }

    public List<GroupResponse> toResponseList(List<Group> groups) {
        return groups.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GroupResponse toCourseResponse(Group group) {
        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .memberCount(group.getUsers() != null ? group.getUsers().size() : 0)
                .courseId(group.getCourse() != null ? group.getCourse().getCourseId() : null)
                .build();
    }
}