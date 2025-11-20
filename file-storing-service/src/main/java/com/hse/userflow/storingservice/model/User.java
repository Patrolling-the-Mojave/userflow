package com.hse.userflow.storingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
