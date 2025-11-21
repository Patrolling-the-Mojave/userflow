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
    @ManyToOne
    private Integer workId;
    @ManyToOne
    private Integer studentId;
    @Column(unique = true)
    private String originalFileName;
    @Column(unique = true)
    private String s3Key;
    private String mimeType;
    private Long fileSize;
    private LocalDateTime uploadedAt;


}
