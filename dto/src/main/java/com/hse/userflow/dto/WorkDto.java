package com.hse.userflow.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkDto {
    private String name;
    private String description;
}
