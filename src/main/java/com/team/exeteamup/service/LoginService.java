package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.LoginRequest;
import com.team.exeteamup.dto.response.LoginResponse;

public interface LoginService {
    public LoginResponse loginGoogle(LoginRequest loginRequest);
}
