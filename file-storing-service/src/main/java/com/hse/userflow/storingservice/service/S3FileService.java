package com.hse.userflow.storingservice.service;

import com.hse.userflow.dto.FileUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {
    FileUploadResponseDto uploadFile(Integer workId, Integer studentId, MultipartFile multipartFile);
    byte[] downloadFile(String s3Key);
    void deleteFile(String s3Key);

}
