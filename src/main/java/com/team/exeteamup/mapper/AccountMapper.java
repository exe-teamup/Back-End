package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountResponse toResponse(Account account) {
        if (account == null) return null;

        AccountResponse response = AccountResponse.builder()
                .accountId(account.getId())
                .email(account.getEmail())
                .role(account.getRole())
                .createdAt(account.getCreatedAt())
                .status(account.getStatus())
                .build();
        return response;
    }
}
