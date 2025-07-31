package com.example.service.impl;

import com.example.domain.RoleName;
import com.example.domain.User;
import com.example.exception.EntityNotFoundException;
import com.example.repository.UserRepository;
import com.example.service.RoleService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User getById(Long id) {
        log.debug("Searching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new EntityNotFoundException(
                            MessageFormat.format("User with id {0} not found", id)
                    );
                });
        log.debug("Found user: ID = {}", id);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        log.debug("Creating new user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug("Adding default USER role to user");
        user.setRole(roleService.findByName(RoleName.USER));
        User createdUser = userRepository.save(user);
        log.debug("User created successfully");
        return createdUser;
    }
}
