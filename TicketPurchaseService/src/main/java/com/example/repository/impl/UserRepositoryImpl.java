package com.example.repository.impl;

import com.example.domain.User;
import com.example.repository.UserRepository;
import com.example.repository.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public Optional<User> findById(Long id) {
        String sql = """
                SELECT
                    u.id as user_id,
                    u.name as user_name,
                    u.surname as user_surname,
                    u.patronymic as user_patronymic,
                    u.login as user_login,
                    u.password as user_password,
                    r.id as role_id,
                    r.role_name
                FROM users u
                LEFT JOIN roles r ON u.role_id = r.id
                WHERE u.id = :id
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql,
                            new MapSqlParameterSource("id", id),
                            userRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = """
                SELECT
                    u.id as user_id,
                    u.name as user_name,
                    u.surname as user_surname,
                    u.patronymic as user_patronymic,
                    u.login as user_login,
                    u.password as user_password,
                    r.id as role_id,
                    r.role_name
                FROM users u
                LEFT JOIN roles r ON u.role_id = r.id
                WHERE u.login = :login
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql,
                            new MapSqlParameterSource("login", login),
                            userRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        String sql = """
            INSERT INTO users (name, surname, patronymic, login, password, role_id)
            VALUES (:name, :surname, :patronymic, :login, :password, :roleId)
            RETURNING id
            """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", user.getName())
                        .addValue("surname", user.getSurname())
                        .addValue("patronymic", user.getPatronymic())
                        .addValue("login", user.getLogin())
                        .addValue("password", user.getPassword())
                        .addValue("roleId", user.getRole().getId()),
                Long.class
        );

        user.setId(id);
        return user;
    }


}
