package com.example.service;

import com.example.domain.Role;
import com.example.domain.RoleName;

public interface RoleService {
    Role findByName(RoleName name);
}
