package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.account.AccountRole;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String mail;
    private AccountRole role;
    private LocalDateTime createdAt;
    private String status;
}
