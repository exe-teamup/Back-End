package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentProfileResponse {
    private long userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String bio;
    private LocalDateTime createdAt;
    private boolean isLeader;
    private String studentStatus;
    private Long groupId;
}
