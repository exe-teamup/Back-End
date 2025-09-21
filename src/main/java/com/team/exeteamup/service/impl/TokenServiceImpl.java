package com.team.exeteamup.service.impl;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    AccountRepository accountRepository;

    private final String SECRET_KEY = "aHVuZ3RydW9uZ3NkZmdmdmRmY3ZkZnZkc3ZlcnMasESFwwWJbn12e2n3one23ior3bqy3";

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //create token
    public String generateToken(Account account) {
        String token = Jwts.builder()
                .subject(account.getUuid() + "")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *24))
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    //verify token
    public Account getAccountByToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String uuidString = claims.getSubject();
        UUID uuid = UUID.fromString(uuidString);

        return accountRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
