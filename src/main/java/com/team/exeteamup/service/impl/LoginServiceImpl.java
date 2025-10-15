package com.team.exeteamup.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.team.exeteamup.dto.request.LoginRequest;
import com.team.exeteamup.dto.response.LoginResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.exception.BadRequestException;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public LoginResponse loginGoogle(LoginRequest loginRequest) {
        try {
            FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(loginRequest.getIdToken());
            String email = decodeToken.getEmail();

            Optional<Account> optionalAccount = accountRepository.findByEmail(email);
            if (optionalAccount.isEmpty()) {
                throw new BadRequestException("Email not registered");
            }
            Account account = optionalAccount.get();

            return LoginResponse.builder()
                    .role(account.getRole())
                    .accountId(account.getAccountId())
                    .build();

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new BadRequestException("Invalid Token");
        }
    }
}

