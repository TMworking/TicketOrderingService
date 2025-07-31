package com.example.repository;

import com.example.domain.Role;
import com.example.domain.RoleName;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(RoleName name);
}
