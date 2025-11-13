package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.request.LoginDemoRequest;
import com.team.exeteamup.dto.request.LoginRequest;
import com.team.exeteamup.dto.response.LoginResponse;

public interface LoginService {
    LoginResponse loginGoogle(LoginRequest loginRequest);
    LoginResponse loginDemoGoogle(LoginDemoRequest request);
}
