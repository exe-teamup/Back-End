package com.team.exeteamup.dto.request;

import com.team.exeteamup.enums.AccountRole;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    private String mail;
    private AccountRole role;
    private LocalDateTime createdAt;
    private String status;
}
