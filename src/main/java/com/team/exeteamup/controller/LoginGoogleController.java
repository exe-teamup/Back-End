package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.LoginRequest;
import com.team.exeteamup.dto.response.LoginResponse;
import com.team.exeteamup.service.impl.LoginServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
public class LoginGoogleController {
    private final LoginServiceImpl loginServiceImpl;

    public LoginGoogleController(LoginServiceImpl loginServiceImpl) {
        this.loginServiceImpl = loginServiceImpl;
    }

    @PostMapping("login-google")
    public ResponseEntity loginGoole(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginServiceImpl.loginGoogle(loginRequest);
        return ResponseEntity.ok(response);
    }
}
