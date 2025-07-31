package com.example.web.controller;

import com.example.mapper.UserMapper;
import com.example.service.AuthService;
import com.example.service.UserService;
import com.example.web.dto.auth.AuthResponse;
import com.example.web.dto.auth.RefreshTokenRequest;
import com.example.web.dto.user.request.UserLoginRequest;
import com.example.web.dto.user.request.UserRegisterRequest;
import com.example.web.dto.user.response.UserRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Создает нового пользователя в системе"
    )
    @PostMapping("/registration")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toRegisterResponse(userService.create(userMapper.toUser(request))));
    }

    @Operation(
            summary = "Аутентификация пользователя",
            description = "Проверяет учетные данные и возвращает JWT-токены"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok().body(authService.login(request));
    }

    @Operation(
            summary = "Обновление токена",
            description = "Генерирует новую пару access/refresh токенов по валидному refresh токену"
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok().body(authService.refreshToken(request));
    }
}
