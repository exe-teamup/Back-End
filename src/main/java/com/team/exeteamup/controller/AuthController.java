package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.LoginRequest;
import com.team.exeteamup.dto.response.LoginResponse;
import com.team.exeteamup.service.inter.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;


    @PostMapping("login-google")
    public ResponseEntity<LoginResponse> loginGoogle(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.loginGoogle(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Không có token hợp lệ để logout");
        }
        return ResponseEntity.ok("Đăng xuất thành công");
    }
}
