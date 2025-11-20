package com.hse.userflow.storingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "works")
public class Work {
    @Id
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

}
