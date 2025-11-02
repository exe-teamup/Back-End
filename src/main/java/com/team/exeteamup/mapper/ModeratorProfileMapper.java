package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.ModeratorProfileResponse;
import com.team.exeteamup.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class ModeratorProfileMapper {
    public ModeratorProfileResponse toResponse(Account account) {
        return ModeratorProfileResponse.builder()
                .accountId(account.getId())
                .email(account.getEmail())
                .role(account.getRole() != null ? account.getRole().name() : null)
                .status(account.getStatus() != null ? account.getStatus().name() : null)
                .createdAt(account.getCreatedAt())
                .build();
    }
}

