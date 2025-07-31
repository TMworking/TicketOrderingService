package com.example.service.impl;

import com.example.domain.RefreshToken;
import com.example.domain.User;
import com.example.security.JwtTokenProvider;
import com.example.security.service.RefreshTokenService;
import com.example.service.AuthService;
import com.example.web.dto.auth.AuthResponse;
import com.example.web.dto.auth.RefreshTokenRequest;
import com.example.web.dto.user.request.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse login(UserLoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();
        String refreshToken = refreshTokenService.generateRefreshToken(user).getToken();
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        return new AuthResponse(refreshToken, accessToken);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken storedToken = refreshTokenService.findByToken(request.getRefreshToken());

        User user = (User) userDetailsService.loadUserByUsername(
                storedToken.getUser().getUsername());

        String refreshToken = refreshTokenService.generateRefreshToken(user).getToken();
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        return new AuthResponse(refreshToken, accessToken);
    }
}
