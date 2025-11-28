package com.hse.userflow.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportDto {
    private Integer workId;
    private String workName;
    private Integer studentId;
    private String studentName;
    private Boolean plagiarismDetected;
    private LocalDateTime uploadedAt;
}
