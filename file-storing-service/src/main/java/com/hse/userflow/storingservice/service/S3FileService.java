package com.hse.userflow.storingservice.service;

import com.hse.userflow.dto.FileUploadResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3FileService {
    FileUploadResponseDto uploadFile(Integer workId, Integer studentId, MultipartFile multipartFile);
    ResponseEntity<byte[]> downloadFile(Integer fileId);
    void deleteFile(Integer fileId);

}
