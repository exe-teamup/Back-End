package com.team.exeteamup.dto.response;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private long studentId;
    private Account accountId;
    private String fullName;
    private String email;
    private String studentCode;
    private String phoneNumber;
    private String bio;
    private LocalDateTime createdAt;
    private Boolean isLeader;
    private StudentStatus studentStatus;

    private Long groupId;
    private String groupName;

    private Long majorId;
    private String majorName;


}
