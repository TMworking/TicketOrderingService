package com.example.service.impl;

import com.example.domain.Role;
import com.example.domain.RoleName;
import com.example.exception.EntityNotFoundException;
import com.example.repository.RoleRepository;
import com.example.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName name) {
        log.debug("Searching role by name: {}", name);
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Role with name {0} not found", name)
                ));
        log.debug("Found role: {}", role);
        return role;
    }
}
