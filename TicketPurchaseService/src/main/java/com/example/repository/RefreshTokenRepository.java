package com.example.repository;


import com.example.domain.RefreshToken;
import com.example.domain.User;

import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken save(RefreshToken refreshToken);
    void deleteAllByUser(User user);
    Long countByUser(User user);
}
