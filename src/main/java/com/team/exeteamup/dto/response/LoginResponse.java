package com.team.exeteamup.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private long studentId;
    private int studentCode;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
    private boolean status;
    private String token;
}
