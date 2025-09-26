package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentProfileResponse {
    private long studentId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String bio;
    private Date createdAt;
    private boolean isLeader;
    private boolean studentStatus;
    private Long groupId;
}
