package com.hse.userflow.storingservice.mapper;

import com.hse.userflow.dto.UserCreateDto;
import com.hse.userflow.dto.UserDto;
import com.hse.userflow.storingservice.model.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static User toEntity(UserCreateDto newUser){
        return User.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserDto toDto(User user){
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
