package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.account.AccountRole;
import com.team.exeteamup.enums.account.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private long accountId;
    private String email;
    private String fullName;
    private AccountRole role;
    private LocalDateTime createdAt;
    private AccountStatus status;
}
