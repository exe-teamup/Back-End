package com.team.exeteamup.service.inter;

import com.team.exeteamup.entity.Account;

public interface TokenService {
    Account getAccountByToken(String token);
    String generateToken(Account account);
}
