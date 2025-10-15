package com.team.exeteamup.dto.response;

import com.team.exeteamup.enums.account.AccountRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private long accountId;
    private AccountRole role;
    private String accessToken;
}
