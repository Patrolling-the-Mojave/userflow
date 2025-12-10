package com.hse.userflow.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateDto {
    @NotNull
    @Size(max = 50)
    private String name;
    @Email
    @NotNull
    private String email;
}
