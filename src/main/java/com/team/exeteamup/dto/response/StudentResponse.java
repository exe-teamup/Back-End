package com.team.exeteamup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private long studentId;
    private String fullName;
    private String email;
    private String studentCode;
    private String phoneNumber;
    private Date createdAt;
    private Long groupId;
    private String groupName;
    private Boolean isLeader;
    private Boolean studentStatus;
}
