package com.hse.userflow.gateway.controller;

import com.hse.userflow.dto.work.WorkCreateDto;
import com.hse.userflow.dto.work.WorkDto;
import com.hse.userflow.gateway.client.WorkStorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/works")
public class WorkController {
    private final WorkStorageClient workStorageClient;

    @PostMapping
    public WorkDto addWork(@Validated @RequestBody WorkCreateDto newWork){
        return workStorageClient.createWork(newWork);
    }

    @DeleteMapping("/{workId}")
    public void deleteWork(@PathVariable Integer workId){
        workStorageClient.deleteWork(workId);
    }

}
