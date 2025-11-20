package com.hse.userflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkCreateDto {
    @Size(max = 50)
    @NotNull
    private String name;
    @NotNull
    private String description;
}
