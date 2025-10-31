package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.service.inter.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping("")
    public ResponseEntity<AccountResponse> login(@RequestBody AccountRequest accountRequest) {
        AccountResponse response = accountService.loginWithEmail(accountRequest.getMail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
