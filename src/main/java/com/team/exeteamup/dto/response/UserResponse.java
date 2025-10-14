package com.team.exeteamup.dto.response;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long courseId;
    private long userId;
    private Account accountId;
    private String fullName;
    private String email;
    private String userCode;
    private String phoneNumber;
    private String bio;
    private LocalDateTime createdAt;
    private Boolean isLeader;
    private UserStatus userStatus;

    private Long groupId;
    private String groupName;

    private Long majorId;
    private String majorName;


}
