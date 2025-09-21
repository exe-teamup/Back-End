package com.team.exeteamup.service;

import com.team.exeteamup.entity.Account;

public interface TokenService {
    String generateToken(Account account);

    Account getAccountByToken(String token);
}
