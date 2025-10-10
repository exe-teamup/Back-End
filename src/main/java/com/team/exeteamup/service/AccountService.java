package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.entity.Account;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface AccountService {
    public Account createAccount(AccountRequest email);
    public AccountResponse loginWithEmail(String email);
    List<Account> presentAccounts(@NotEmpty List<Long> accountIds);
    Account getAccountById(Long accountId);
}
