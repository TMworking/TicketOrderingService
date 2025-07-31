package com.example.repository.mapper;

import com.example.domain.Role;
import com.example.domain.RoleName;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Role.builder()
                .id(rs.getLong("role_id"))
                .roleName(RoleName.valueOf(rs.getString("role_name")))
                .build();
    }
}
