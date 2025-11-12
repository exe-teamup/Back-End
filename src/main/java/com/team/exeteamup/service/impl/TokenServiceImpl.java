package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.service.inter.AccountService;
import com.team.exeteamup.service.inter.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final AccountService accountService;

    @Value("${TOKEN_SECRET_KEY}")
    private String SECRET_KEY;

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Account account) {
        String token = Jwts.builder()
                .setSubject(String.valueOf(account.getId()))
                .claim("role", account.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 ngày
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }


    public Account getAccountByToken(String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = Jwts.parser()
                    .verifyWith(getSigninKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            long accountId = Long.parseLong(claims.getSubject());

            return accountService.getAccountById(accountId);

        } catch (ExpiredJwtException e) {
            throw new AppException("Token đã hết hạn");
        } catch (JwtException e) {
            throw new AppException("Token không hợp lệ");
        } catch (NumberFormatException e) {
            throw new AppException("Định dạng ID tài khoản trong token không hợp lệ");
        } catch (Exception e) {
            throw new AppException("Lỗi không xác định khi xử lý token");
        }
    }
}
