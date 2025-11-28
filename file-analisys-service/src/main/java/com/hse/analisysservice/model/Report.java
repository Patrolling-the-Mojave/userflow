package com.hse.analisysservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
public class Report {
    @Id
    private Integer id;
    private Integer workId;
    private String workName;
    private Integer studentId;
    private String studentName;
    private Boolean plagiarismDetected;
    private LocalDateTime uploadedAt;
}
