package com.example.security.service;


import com.example.domain.RefreshToken;
import com.example.domain.User;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(User user);
    RefreshToken findByToken(String token);
}
