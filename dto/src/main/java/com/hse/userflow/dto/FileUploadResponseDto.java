package com.hse.userflow.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileUploadResponseDto {
    private Integer fileId;
    private String fileName;
    private String fileUrl;
    private LocalDateTime loadedAt;
}
