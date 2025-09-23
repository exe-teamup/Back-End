package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private UUID accountId;
    private String email;
    private String fullName;
    private AccountRole role;
    private LocalDateTime createdAt;
    private String status;
}
