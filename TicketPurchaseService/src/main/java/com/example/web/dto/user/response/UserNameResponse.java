package com.example.web.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNameResponse {

    private String name;

    private String surname;

    private String patronymic;
}
