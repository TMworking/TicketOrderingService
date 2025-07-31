package com.example.web.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = "User name shouldn't be empty")
    @Size(min = 1, max = 50, message = "Name min 1 char, max 50 chars")
    private String name;

    @NotBlank(message = "User surname shouldn't be empty")
    @Size(min = 1, max = 50, message = "Surname min 1 char, max 50 chars")
    private String surname;

    @Size(max = 50, message = "Patronymic max 50 chars")
    private String patronymic;

    @NotBlank(message = "User login shouldn't be empty")
    @Email
    private String login;

    @NotBlank(message = "User password shouldn't be empty")
    @Size(min = 4, max = 100)
    private String password;
}
