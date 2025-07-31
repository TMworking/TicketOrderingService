package com.example.mapper;

import com.example.domain.User;
import com.example.web.dto.user.request.UserRegisterRequest;
import com.example.web.dto.user.response.UserRegisterResponse;
import com.example.web.dto.user.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    User toUser(UserRegisterRequest request);

    UserRegisterResponse toRegisterResponse(User user);
}
