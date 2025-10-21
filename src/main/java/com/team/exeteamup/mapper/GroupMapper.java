package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.group.*;
import com.team.exeteamup.entity.Course;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.GroupTemplate;
import com.team.exeteamup.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    public GroupResponse toResponse(Group group) {
        if (group == null) return null;

        GroupMemberResponse leader = null;
        if (group.getUsers() != null) {
            leader = group.getUsers().stream()
                    .filter(User::getIsLeader)
                    .findFirst()
                    .map(user -> GroupMemberResponse.builder()
                            .studentId(user.getUserId())
                            .studentName(user.getFullName())
                            .isLeader(true)
                            .majorName(user.getMajor() != null ? user.getMajor().getMajorName() : null)
                            .build())
                    .orElse(null);
        }

        List<GroupMemberResponse> memberResponses = group.getUsers() == null ? List.of() :
                group.getUsers().stream().map(user ->
                        GroupMemberResponse.builder()
                                .studentId(user.getUserId())
                                .studentName(user.getFullName())
                                .isLeader(user.getIsLeader())
                                .majorName(user.getMajor() != null ? user.getMajor().getMajorName() : null)
                                .build()
                ).collect(Collectors.toList());

        GroupCourseResponse courseResponse = null;
        if (group.getCourse() != null) {
            Course course = group.getCourse();
            courseResponse = GroupCourseResponse.builder()
                    .courseId(course.getCourseId())
                    .courseCode(course.getCourseCode())
                    .build();
        }

        TemplateResponse templateResponse = null;
        if (group.getGroupTemplate() != null) {
            GroupTemplate template = group.getGroupTemplate();
            templateResponse = TemplateResponse.builder()
                    .maxMember(template.getMaxMember())
                    .template(template.getTemplate())
                    .build();
        }

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .leader(leader)
                .members(memberResponses)
                .templates(templateResponse)
                .memberCount(group.getUsers() != null ? group.getUsers().size() : 0)
                .groupStatus(group.getGroupStatus())
                .course(courseResponse)
                .build();
    }

    public List<GroupResponse> toResponseList(List<Group> groups) {
        return groups.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GroupResponse toCourseResponse(Group group) {
        if (group == null) return null;

        GroupCourseResponse courseResponse = null;
        if (group.getCourse() != null) {
            courseResponse = GroupCourseResponse.builder()
                    .courseId(group.getCourse().getCourseId())
                    .courseCode(group.getCourse().getCourseCode())
                    .build();
        }

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .memberCount(group.getUsers() != null ? group.getUsers().size() : 0)
                .course(courseResponse)
                .build();
    }

    public GroupRegisterLecturerResponse toGroupRegisterLecturerResponse(Group group) {
        return GroupRegisterLecturerResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .build();
    }
}
