package com.example.web.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    @NotBlank(message = "User email shouldn't be empty")
    private String email;
    @NotBlank(message = "User password shouldn't be empty")
    private String password;
}
