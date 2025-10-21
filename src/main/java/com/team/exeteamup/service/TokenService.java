package com.team.exeteamup.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.team.exeteamup.entity.Account;
import io.jsonwebtoken.Claims;

public interface TokenService {
    Account getAccountByToken(String token);
    String generateToken(Account account);
}
