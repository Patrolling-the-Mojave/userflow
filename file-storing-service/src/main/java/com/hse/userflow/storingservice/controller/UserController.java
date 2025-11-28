package com.hse.userflow.storingservice.controller;

import com.hse.userflow.dto.UserCreateDto;
import com.hse.userflow.dto.UserDto;
import com.hse.userflow.storingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Storage", description = "api для работы с пользователями")
public class UserController {
    private final UserService userService;

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
    public UserDto createUser(@RequestBody UserCreateDto newUser) {
        return userService.addUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable Integer userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
