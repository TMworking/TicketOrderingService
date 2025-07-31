package com.example.repository.mapper;

import com.example.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class RefreshTokenRowMapper implements RowMapper<RefreshToken> {

    private final UserRowMapper userRowMapper;

    @Override
    public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RefreshToken.builder()
                .id(rs.getLong("refresh_token_id"))
                .token(rs.getString("token"))
                .expiryDate(rs.getTimestamp("expiry_date").toInstant())
                .user(rs.getObject("user_id", Long.class) != null ?
                        userRowMapper.mapRow(rs, rowNum) : null)
                .build();
    }
}
