package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {
    Account createAccount(AccountRequest email);
    AccountResponse loginWithEmail(String email);
    List<Account> presentAccounts(@NotEmpty List<Long> accountIds);
    Account getAccountById(Long accountId);
    Account findAccountByUserId(Long userId);
}
