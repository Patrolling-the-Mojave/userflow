package com.hse.analisysservice.service;

import com.hse.userflow.dto.ReportDto;

import java.util.List;

public interface ReportService {
    ReportDto findByWorkIdAndStudentId(Integer studentId, Integer workId);

    List<ReportDto> findAllByWorkId(Integer workId);

}
