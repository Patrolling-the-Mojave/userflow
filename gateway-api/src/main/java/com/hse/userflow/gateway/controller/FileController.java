package com.hse.userflow.gateway.controller;


import com.hse.userflow.dto.FileDto;
import com.hse.userflow.gateway.client.FileClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileClient fileClient;

    @PostMapping("/works/{workId}/students/{studentId}/upload")
    public FileDto uploadFile(@PathVariable Integer workId,
                              @PathVariable Integer studentId,
                              @RequestParam("file") MultipartFile file) {
        return fileClient.uploadFile(file, workId, studentId);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer fileId) {
        return fileClient.downloadFile(fileId);
    }

    @DeleteMapping("/{fileId}")
    public void deleteFile(@PathVariable Integer fileId){
        fileClient.deleteFile(fileId);
    }
}

