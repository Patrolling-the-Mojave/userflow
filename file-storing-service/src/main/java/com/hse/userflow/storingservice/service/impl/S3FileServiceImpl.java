package com.hse.userflow.storingservice.service.impl;

import com.hse.userflow.dto.FileUploadResponseDto;
import com.hse.userflow.storingservice.exception.FileDownloadException;
import com.hse.userflow.storingservice.exception.FileUploadException;
import com.hse.userflow.storingservice.exception.NotFoundException;
import com.hse.userflow.storingservice.model.File;
import com.hse.userflow.storingservice.repository.FileRepository;
import com.hse.userflow.storingservice.repository.UserRepository;
import com.hse.userflow.storingservice.repository.WorkRepository;
import com.hse.userflow.storingservice.service.S3FileService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static com.hse.userflow.storingservice.mapper.FileMapper.toDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final WorkRepository workRepository;
    private final S3Client s3Client;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
            log.info("бакет {} уже существует", bucketName);
        } catch (NoSuchBucketException exception) {
            s3Client.createBucket(CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
            log.info("бакет{} успешно создан", bucketName);
        }
    }

    @Override
    @Transactional
    public void deleteFile(Integer fileId) {
        File file = getFileById(fileId);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getS3Key())
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        fileRepository.deleteById(fileId);
        log.debug("файл успешно удален");
    }

    @Override
    @Transactional
    public FileUploadResponseDto uploadFile(Integer workId, Integer studentId, MultipartFile multipartFile) {
        doesUserExists(studentId);
        doesWorkExists(workId);
        String s3Key = generateS3Key(workId, studentId, multipartFile.getOriginalFilename());
        try {
            uploadToS3(multipartFile, s3Key);
            File file = File.builder()
                    .originalFileName(multipartFile.getOriginalFilename())
                    .fileSize(multipartFile.getSize())
                    .uploadedAt(LocalDateTime.now())
                    .s3Key(s3Key)
                    .mimeType(multipartFile.getContentType())
                    .workId(workId)
                    .studentId(studentId)
                    .build();
            return toDto(fileRepository.save(file));
        } catch (IOException exception) {
            throw new FileUploadException("не удалось загрузить файл", exception);
        }
    }

    private void uploadToS3(MultipartFile multipartFile, String s3Key) throws IOException {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();
        s3Client.putObject(putRequest, RequestBody.fromBytes(multipartFile.getBytes()));
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Integer fileId) {
        File file = getFileById(fileId);
        try {
            byte[] content = getFileContentFromS3(file.getS3Key());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(file.getMimeType()));
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename(file.getOriginalFileName(), StandardCharsets.UTF_8)
                    .build());
            headers.setContentLength(file.getFileSize());
            headers.setCacheControl("no-cache");
            log.debug("Файл {} успешно подготовлен для скачивания", fileId);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (Exception exception){
            throw new FileDownloadException("произошла ошибка при скачивании файла", exception);
        }
    }

    public byte[] getFileContentFromS3(String s3Key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();
        ResponseBytes<GetObjectResponse> bytes = s3Client.getObjectAsBytes(getObjectRequest);
        log.debug("файл успешно извлечен");
        return bytes.asByteArray();
    }

    private String generateS3Key(Integer workId, Integer studentId, String originalFileName) {
        String extension = getFileExtension(originalFileName);
        String timestamp = String.valueOf(System.currentTimeMillis());
        return String.format("works/%d/students/%d/%s%s", workId, studentId, timestamp, extension);
    }

    private String getFileExtension(String fileName) {
        return fileName != null && fileName.contains(".") ?
                fileName.substring(fileName.lastIndexOf('.')) : "";
    }

    private void doesUserExists(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("пользователь с id " + userId + " не найден");
        }
    }

    private void doesWorkExists(Integer workId) {
        if (!workRepository.existsById(workId)) {
            throw new NotFoundException("работа с id " + workId + " не найдена");
        }
    }

    private File getFileById(Integer fileId) {
        return fileRepository.findById(fileId).orElseThrow(() ->
                new NotFoundException("файл с id " + fileId + " не найден"));
    }
}
