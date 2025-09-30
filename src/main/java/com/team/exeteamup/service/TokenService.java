package com.team.exeteamup.service;

import com.team.exeteamup.entity.Account;

public interface TokenService {
    Account getAccountByToken(String token);
}
