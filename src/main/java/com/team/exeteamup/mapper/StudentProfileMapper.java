package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.StudentProfileResponse;
import com.team.exeteamup.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileMapper {
    public StudentProfileResponse toResponse(User user) {
        return StudentProfileResponse.builder()
                .userId(user.getUserId())
                .courseId(user.getCourse().getCourseId())
                .fullName(user.getFullName())
                .email(user.getAccount().getEmail())
                .phoneNumber(user.getPhoneNumber())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .isLeader(user.getIsLeader())
                .studentStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null)
                .groupId(user.getGroup() != null ? user.getGroup().getGroupId() : null)
                .build();
    }
}
