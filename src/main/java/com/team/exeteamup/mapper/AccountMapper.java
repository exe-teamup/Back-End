package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountResponse toResponse(Account account) {
        if (account == null) return null;

        AccountResponse response = AccountResponse.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .role(account.getRole())
                .createdAt(account.getCreatedAt())
                .status(String.valueOf(account.isStatus()))
                .build();
        return response;
    }
}
