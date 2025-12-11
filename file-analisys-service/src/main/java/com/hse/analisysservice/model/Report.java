package com.hse.analisysservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name = "reports")
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer workId;
    private String workName;
    private Integer studentId;
    private String studentName;
    private Boolean plagiarismDetected;
    private LocalDateTime uploadedAt;
}
