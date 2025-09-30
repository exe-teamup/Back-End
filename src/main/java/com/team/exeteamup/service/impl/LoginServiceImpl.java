package com.team.exeteamup.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.team.exeteamup.dto.request.LoginRequest;
import com.team.exeteamup.dto.response.LoginResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    @Override
    public LoginResponse loginGoogle(LoginRequest loginRequest) {
        try {
            FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(loginRequest.getIdToken());
            String email = decodeToken.getEmail();
            System.out.println("Decoded Firebase email: " + email);

            Optional<Account> optionalAccount = accountRepository.findByEmail(email);
            if (optionalAccount.isEmpty()) {
                throw new RuntimeException("Email not registered");
            }
            Account account = optionalAccount.get();

            String accessToken = tokenServiceImpl.generateAccessToken(account);
            String refreshToken = tokenServiceImpl.generateRefreshToken(account);
            long expiresIn = tokenServiceImpl.getAccessTokenExpiration();

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(expiresIn)
                    .build();

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Firebase token");
        }
    }
}

