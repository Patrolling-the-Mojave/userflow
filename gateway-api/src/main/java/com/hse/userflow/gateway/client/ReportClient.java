package com.hse.userflow.gateway.client;

import com.hse.userflow.dto.ReportDto;
import com.hse.userflow.gateway.exception.GateWayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportClient {
    private final RestTemplate restTemplate;

    @Value("${analysis-service.url}")
    private String serviceUrl;

    public ReportDto findReportByWorkIdAndStudentId(Integer workId, Integer studentId) {
        String url = serviceUrl + "/api/reports/works/{workId}/students/{studentId}";
        try {
            ResponseEntity<ReportDto> response = restTemplate.getForEntity(url, ReportDto.class, workId, studentId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new GateWayException("не удалось получить данные, код ответа{}" + response.getStatusCode());
            }
        } catch (ResourceAccessException exception) {
            throw new GateWayException(exception.getMessage(), exception);
        }
    }

    public List<ReportDto> findAllReportsByWorkId(Integer workId) {
        String url = serviceUrl + "/api/reports/works/{workId}";
        try {
            ResponseEntity<ReportDto[]> response = restTemplate.getForEntity(url, ReportDto[].class, workId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            } else {
                throw new GateWayException("не удалось получить данные, код ответа{}" + response.getStatusCode());
            }
        } catch (ResourceAccessException exception) {
            throw new GateWayException(exception.getMessage());
        }
    }
}
