package com.example.repository.impl;

import com.example.domain.RefreshToken;
import com.example.domain.User;
import com.example.repository.RefreshTokenRepository;
import com.example.repository.mapper.RefreshTokenRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RefreshTokenRowMapper refreshTokenRowMapper;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        String sql = """
                SELECT
                    rt.id as refresh_token_id,
                    rt.token as token,
                    rt.expiry_date,
                    u.id as user_id,
                    u.name as user_name,
                    u.surname as user_surname,
                    u.patronymic as user_patronymic,
                    u.login as user_login,
                    u.password as user_password,
                    r.id as role_id,
                    r.role_name
                FROM refresh_tokens rt
                LEFT JOIN users u ON rt.user_id = u.id
                LEFT JOIN roles r ON u.role_id = r.id
                WHERE rt.token = :token
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql,
                            new MapSqlParameterSource("token", token),
                            refreshTokenRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        String sql = """
                INSERT INTO refresh_tokens (token, user_id, expiry_date)
                VALUES (:token, :userId, :expiryDate)
                RETURNING id
                """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource()
                        .addValue("token", refreshToken.getToken())
                        .addValue("expiryDate", refreshToken.getExpiryDate().atOffset(ZoneOffset.UTC))
                        .addValue("userId", refreshToken.getUser().getId()),
                Long.class
        );

        refreshToken.setId(id);

        return refreshToken;
    }

    @Override
    public void deleteAllByUser(User user) {
        String sql = """
                DELETE FROM refresh_tokens
                WHERE user_id = :userId
                """;

        jdbcTemplate.update(
                sql,
                new MapSqlParameterSource("userId", user.getId())
        );
    }

    @Override
    public Long countByUser(User user) {
        String sql = """
                SELECT COUNT(*)
                FROM refresh_tokens
                WHERE user_id = :userId
                """;

        return jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("userId", user.getId()),
                Long.class
        );
    }
}
