package com.example.service;

import com.example.web.dto.auth.AuthResponse;
import com.example.web.dto.auth.RefreshTokenRequest;
import com.example.web.dto.user.request.UserLoginRequest;

public interface AuthService {
    AuthResponse login(UserLoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
}
