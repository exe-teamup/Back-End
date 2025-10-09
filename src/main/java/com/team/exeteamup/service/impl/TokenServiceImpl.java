package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.service.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    AccountRepository accountRepository;

    private final String SECRET_KEY = "${TOKEN_SECRET_KEY}";
    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 24 * 7;


    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //verify token
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

            return accountRepository.findById(accountId)
                    .orElseThrow(() -> new AppException("Không tìm thấy tài khoản liên kết với token"));

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
