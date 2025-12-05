package com.hse.userflow.gateway.controller;

import com.hse.userflow.dto.ReportDto;
import com.hse.userflow.gateway.client.ReportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    private final ReportClient reportClient;

    @GetMapping("/works/{workId}/students/{studentId}")
    public ReportDto findReportByWorkIdAndStudentId(@PathVariable Integer workId,
                                                    @PathVariable Integer studentId){
        return reportClient.findReportByWorkIdAndStudentId(workId, studentId);
    }

    @GetMapping("/works/{workId}")
    public List<ReportDto> findReportsByWorkId(@PathVariable Integer workId){
        return reportClient.findAllReportsByWorkId(workId);
    }
}
