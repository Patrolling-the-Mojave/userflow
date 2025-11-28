package com.hse.analisysservice.service.impl;

import com.hse.analisysservice.repository.ReportRepository;
import com.hse.analisysservice.service.ReportService;
import com.hse.userflow.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hse.analisysservice.mapper.ReportMapper.toDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public List<ReportDto> findAllByWorkId(Integer workId) {
        log.debug("запрос на получение всех отчетов для работы с id{}", workId);
        return toDto(reportRepository.findAllByWorkId(workId));

    }

    @Override
    public ReportDto findByWorkIdAndStudentId(Integer studentId, Integer workId) {
        log.debug("запрос на получение отчета по работе{} студента {}", workId, studentId);
        return toDto(reportRepository.findByWorkIdAndStudentId(workId, studentId));
    }
}
