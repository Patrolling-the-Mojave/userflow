package com.hse.userflow.storingservice.service;

import com.hse.userflow.dto.WorkCreateDto;
import com.hse.userflow.dto.WorkDto;

public interface WorkService {
    WorkDto addWord(WorkCreateDto newWork);
    void deleteWork(Integer workId);
}
