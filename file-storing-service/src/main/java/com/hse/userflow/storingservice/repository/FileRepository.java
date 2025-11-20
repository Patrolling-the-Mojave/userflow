package com.hse.userflow.storingservice.repository;

import com.hse.userflow.storingservice.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
