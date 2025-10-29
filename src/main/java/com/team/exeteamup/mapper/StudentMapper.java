package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.UserStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setCourseId(user.getCourse().getCourseId());
        response.setAccountId(user.getAccount().getId());
        response.setUserId(user.getUserId());
        response.setFullName(user.getFullName());
        response.setUserCode(user.getUserCode());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setBio(user.getBio());
        response.setCreatedAt(user.getCreatedAt());

        if (user.getAccount() != null) {
            response.setEmail(user.getAccount().getEmail());
        }

        if (user.getGroup() != null) {
            response.setGroupId(user.getGroup().getGroupId());
            response.setGroupName(user.getGroup().getGroupName());
        }

        if (user.getMajor() != null) {
            response.setMajorId(user.getMajor().getMajorId());
            response.setMajorName(user.getMajor().getMajorName());
        }

        response.setUserStatus(
                user.getUserStatus() != null ? UserStatus.valueOf(user.getUserStatus().name()) : null
        );

        response.setIsLeader(user.getIsLeader() != null ? user.getIsLeader() : false);

        return response;
    }

    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}