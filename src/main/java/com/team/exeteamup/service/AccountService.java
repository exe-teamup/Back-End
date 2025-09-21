package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AccountService {
    public Account createAccount(AccountRequest email);
    public AccountResponse loginWithEmail(String email);
}
