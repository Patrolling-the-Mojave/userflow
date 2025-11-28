package com.hse.analisysservice.service.impl;

import com.hse.analisysservice.model.Report;
import com.hse.analisysservice.repository.ReportRepository;
import com.hse.analisysservice.service.ReportAnalysisService;
import com.hse.analisysservice.service.client.FileStorageClient;
import com.hse.userflow.dto.AnalysisRequest;
import com.hse.userflow.dto.FileContentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportAnalysisServiceImpl implements ReportAnalysisService {
    private final ReportRepository reportRepository;
    private final FileStorageClient storageClient;

    @Override
    @Transactional
    public void analyzeReport(AnalysisRequest request) {
        FileContentDto currentFile = storageClient.getFileContentFromStorage(request.getFileId());
        List<FileContentDto> earlierReports = storageClient.findAllEarlierReports(request.getFileId());

        String currentFileHash = calculateHash(currentFile.getContent());
        boolean isPlagiarism = false;

        for (FileContentDto earlierReport : earlierReports) {
            String fileHash = calculateHash(earlierReport.getContent());
            if (currentFileHash.equals(fileHash)) {
                isPlagiarism = true;
                break;
            }
        }

        Report report = Report.builder()
                .workId(currentFile.getFileId())
                .uploadedAt(LocalDateTime.now())
                .studentName(currentFile.getUserName())
                .workName(currentFile.getWorkName())
                .studentId(currentFile.getStudentId())
                .plagiarismDetected(isPlagiarism)
                .build();
        reportRepository.save(report);
    }


    private String calculateHash(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(content);
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException("алгоритм хеширования не найден");
        }
    }
}
