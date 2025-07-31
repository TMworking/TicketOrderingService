package com.example.service;

import com.example.domain.User;

public interface UserService {
    User getById(Long id);
    User create(User user);
}
