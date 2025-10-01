package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.AccountRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private long accountId;
    private AccountRole role;
}
