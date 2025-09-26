package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.dto.response.AccountResponse;
import com.team.exeteamup.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public ResponseEntity<?> getm() {
        return ResponseEntity.ok("test controller");
    }

    @PostMapping("")
    public ResponseEntity<AccountResponse> login(@RequestBody AccountRequest accountRequest) {
        AccountResponse response = accountService.loginWithEmail(accountRequest.getMail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
