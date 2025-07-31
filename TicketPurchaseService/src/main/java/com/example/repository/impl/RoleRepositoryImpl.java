package com.example.repository.impl;

import com.example.domain.Role;
import com.example.domain.RoleName;
import com.example.repository.RoleRepository;
import com.example.repository.mapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleRowMapper;

    @Override
    public Optional<Role> findByName(RoleName name) {
        String sql = """
                SELECT
                    r.id as role_id,
                    r.role_name
                FROM roles r
                WHERE r.role_name = :name
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql,
                            new MapSqlParameterSource("name", name.name()),
                            roleRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
