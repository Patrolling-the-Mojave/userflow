package com.hse.userflow.storingservice.controller;

import com.hse.userflow.dto.FileDto;
import com.hse.userflow.storingservice.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class PublicFileController {
    private final S3FileService fileService;

    @PostMapping("/works/{workId}/users/{userId}/upload")
    public FileDto upload(@RequestParam("file") MultipartFile file,
                          @PathVariable Integer workId,
                          @PathVariable Integer userid
    ) {
        return fileService.uploadFile(workId, userid, file);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable Integer fileId) {
        return fileService.downloadFile(fileId);
    }

    @DeleteMapping("/delete/{fileId}")
    public void delete(@PathVariable Integer fileId) {
        fileService.deleteFile(fileId);
    }

}
