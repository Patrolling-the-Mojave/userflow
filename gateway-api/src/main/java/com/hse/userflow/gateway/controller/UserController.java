package com.hse.userflow.gateway.controller;

import com.hse.userflow.dto.UserCreateDto;
import com.hse.userflow.dto.UserDto;
import com.hse.userflow.gateway.client.UserStorageClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserStorageClient userStorageClient;

    @Operation(summary = "добавление нового пользователя")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "пользователь успешно добавлен",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "неверные данные в теле запроса"
            )
    })
    @PostMapping
    public UserDto createUser(@Validated @RequestBody UserCreateDto newUser){
        return userStorageClient.createUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Integer userId){
        return userStorageClient.getUserById(userId);
    }

    @DeleteMapping("/userId")
    public void deleteUserById(@PathVariable Integer userId){
        userStorageClient.deleteUser(userId);
    }
}
