package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.LoginRequest;
import com.team.exeteamup.dto.response.LoginResponse;
import com.team.exeteamup.service.LoginService;
import com.team.exeteamup.service.impl.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class LoginGoogleController {

    private final LoginService loginService;


    @PostMapping("login-google")
    public ResponseEntity<?> loginGoogle(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.loginGoogle(loginRequest);
        return ResponseEntity.ok(response);
    }
}
