package com.hse.analisysservice.service;

import com.hse.userflow.dto.AnalysisRequest;

public interface ReportAnalysisService {
    void analyzeReport(AnalysisRequest request);
}
