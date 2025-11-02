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
public class AdminProfileResponse {
    private Long accountId;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;
}

