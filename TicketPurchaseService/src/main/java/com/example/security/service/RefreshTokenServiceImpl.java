package com.example.security.service;

import com.example.domain.RefreshToken;
import com.example.domain.User;
import com.example.exception.EntityNotFoundException;
import com.example.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh.max-count}")
    private Integer maxRefreshTokensCount;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public RefreshToken generateRefreshToken(User user) {
        log.debug("Fetching user's current count of refresh tokens: ID={}", user.getId());
        Long tokensCount = refreshTokenRepository.countByUser(user);

        if (tokensCount >= maxRefreshTokensCount) {
            log.debug("Deleting all old user refresh tokens: ID={}", user.getId());
            refreshTokenRepository.deleteAllByUser(user);
            log.debug("Successfully deleted all old refresh tokens");
        }

        log.debug("Generating new refresh token for user: ID={}", user.getId());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpiration));
        refreshToken.setUser(user);

        RefreshToken createdRefreshToken = refreshTokenRepository.save(refreshToken);
        log.debug("Successfully created new refresh token for user: ID={}", user.getId());

        return createdRefreshToken;
    }

    @Override
    public RefreshToken findByToken(String token) {
        log.debug("Searching refresh token by token");
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Refresh token not found");
                    return new EntityNotFoundException("Invalid refresh token");
                });
        log.debug("Found refresh token");
        return refreshToken;
    }
}
