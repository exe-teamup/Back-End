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
    private String token;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
