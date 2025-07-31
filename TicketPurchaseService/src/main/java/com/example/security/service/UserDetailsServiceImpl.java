package com.example.security.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Searching user by login: {}", username);
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> {
                    log.error("User with login {} not found", username);
                    return new UsernameNotFoundException("User not found");
                });
        log.debug("Found user: ID = {}", user.getId());
        return user;
    }
}
