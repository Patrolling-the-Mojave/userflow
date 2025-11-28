package com.hse.analisysservice.mapper;

import com.hse.analisysservice.model.Report;
import com.hse.userflow.dto.FileContentDto;
import com.hse.userflow.dto.ReportDto;

import java.util.List;

public class ReportMapper {
    public static ReportDto toDto(Report report){
        return ReportDto.builder()
                .workId(report.getWorkId())
                .workName(report.getWorkName())
                .studentId(report.getStudentId())
                .studentName(report.getStudentName())
                .plagiarismDetected(report.getPlagiarismDetected())
                .uploadedAt(report.getUploadedAt())
                .build();
    }

    public static List<ReportDto> toDto(List<Report> reports){
        return reports.stream().map(ReportMapper::toDto).toList();
    }
}
