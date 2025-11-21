package com.hse.userflow.storingservice.service;

import com.hse.userflow.dto.UserCreateDto;
import com.hse.userflow.dto.UserDto;

public interface UserService {

    UserDto addUser(UserCreateDto newUser);
    void deleteUser(Integer userId);
    UserDto findById(Integer userId);

}
