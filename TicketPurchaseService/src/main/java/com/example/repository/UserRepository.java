package com.example.repository;

import com.example.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByLogin(String login);

    User save(User user);
}
