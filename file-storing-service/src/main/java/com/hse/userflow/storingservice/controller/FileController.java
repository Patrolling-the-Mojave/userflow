package com.hse.userflow.storingservice.controller;

import com.hse.userflow.dto.FileUploadResponseDto;
import com.hse.userflow.storingservice.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final S3FileService fileService;

    @PostMapping("/work/{workId}/user/{userId}/upload")
    public FileUploadResponseDto upload(@RequestParam("file") MultipartFile multipartFile,
                                        @PathVariable Integer workId,
                                        @PathVariable Integer userid
                                        ){
        return fileService.uploadFile(workId, userid, multipartFile);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable Integer fileId){
        return fileService.downloadFile(fileId);
    }

    @DeleteMapping("/delete/{fileId}")
    public void delete(@PathVariable Integer fileId){
        fileService.deleteFile(fileId);
    }

}
