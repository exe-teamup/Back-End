package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.AccountRequest;
import com.team.exeteamup.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public ResponseEntity<?> getm() {
        return ResponseEntity.ok("test controller");
    }
}
