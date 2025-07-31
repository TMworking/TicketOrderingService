package com.example.repository.mapper;

import com.example.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class UserRowMapper implements RowMapper<User> {

    private final RoleRowMapper roleRowMapper;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .name(rs.getString("user_name"))
                .surname(rs.getString("user_surname"))
                .patronymic(rs.getString("user_patronymic"))
                .login(rs.getString("user_login"))
                .password(rs.getString("user_password"))
                .role(rs.getObject("role_id", Long.class) != null ?
                        roleRowMapper.mapRow(rs, rowNum) : null)
                .build();
    }
}
