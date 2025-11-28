package com.hse.analisysservice.repository;

import com.hse.analisysservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAllByWorkId(Integer workId);

    Report findByWorkIdAndStudentId(Integer workId, Integer studentId);
}
