package com.hse.userflow.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileDto {
    private Integer fileId;
    private String fileName;
    private LocalDateTime loadedAt;
}
