package com.hse.userflow.gateway.client;

import com.hse.userflow.dto.FileDto;
import com.hse.userflow.gateway.exception.FileNotFoundException;
import com.hse.userflow.gateway.exception.FileStorageException;
import com.hse.userflow.gateway.exception.GateWayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileClient {
    private final RestTemplate restTemplate;

    @Value("${storing-service.url}")
    private String storingServiceUrl;

    public FileDto uploadFile(MultipartFile file, Integer workId, Integer studentId) {
        String url = storingServiceUrl + "/api/files/works/{workId}/students/{studentId}/upload";
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new MultipartFileResource(file));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<FileDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    FileDto.class,
                    workId,
                    studentId
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.debug("файл пользователя{} успешно загружен", studentId);
                return response.getBody();
            } else {
                throw new FileStorageException("не удалось загрузить файл");
            }
        } catch (ResourceAccessException exception) {
            throw new GateWayException("");
        }
    }
    public ResponseEntity<byte[]> downloadFile(Integer fileId) {
        String url = storingServiceUrl + "/api/files/download/{fileId}";

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    byte[].class,
                    fileId
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("File downloaded successfully. File ID: {}", fileId);
                return response;
            } else {
                throw new FileStorageException("Download failed with status: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException.NotFound e) {
            log.error("File not found with ID: {}", fileId);
            throw new FileNotFoundException("File not found with ID: " + fileId);
        } catch (ResourceAccessException e) {
            log.error("File storage service is unavailable: {}", storingServiceUrl, e);
            throw new FileStorageException("File storage service is unavailable", e);
        } catch (Exception e) {
            log.error("Error downloading file with ID: {}", fileId, e);
            throw new FileStorageException("Download failed for file: " + fileId, e);
        }
    }

    public void deleteFile(Integer fileId) {
        String url = storingServiceUrl + "/api/files/delete/{fileId}";

        try {
            restTemplate.delete(url, fileId);
            log.debug("File deleted successfully. File ID: {}", fileId);

        } catch (HttpClientErrorException.NotFound e) {
            log.warn("File not found for deletion. File ID: {}", fileId);
            throw new FileNotFoundException("File not found with ID: " + fileId);
        } catch (ResourceAccessException e) {
            log.error("File storage service is unavailable: {}", storingServiceUrl, e);
            throw new FileStorageException("File storage service is unavailable", e);
        } catch (Exception e) {
            log.error("Error deleting file with ID: {}", fileId, e);
            throw new FileStorageException("Delete failed for file: " + fileId, e);
        }
    }
}

class MultipartFileResource implements Resource {
    private final MultipartFile multipartFile;

    public MultipartFileResource(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return multipartFile.getInputStream();
    }

    @Override
    public boolean exists() {
        return !multipartFile.isEmpty();
    }

    @Override
    public boolean isReadable() {
        return exists();
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public URL getURL() throws IOException {
        throw new IOException("Method not supported");
    }

    @Override
    public URI getURI() throws IOException {
        throw new IOException("Method not supported");
    }

    @Override
    public File getFile() throws IOException {
        throw new IOException("Method not supported");
    }

    @Override
    public long contentLength() throws IOException {
        return multipartFile.getSize();
    }

    @Override
    public long lastModified() throws IOException {
        return 0;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        throw new IOException("Method not supported");
    }

    @Override
    public String getFilename() {
        return multipartFile.getOriginalFilename();
    }

    @Override
    public String getDescription() {
        return "MultipartFile resource: " + multipartFile.getOriginalFilename();
    }
}
