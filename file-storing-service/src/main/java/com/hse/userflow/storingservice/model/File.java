package com.hse.userflow.storingservice.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
@Builder
public class File {
    @Id
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;
    @Column(unique = true, nullable = false)
    private String originalFileName;
    @Column(unique = true, nullable = false)
    private String s3Key;
    private String mimeType;
    private Long fileSize;
    private LocalDateTime uploadedAt;
}
