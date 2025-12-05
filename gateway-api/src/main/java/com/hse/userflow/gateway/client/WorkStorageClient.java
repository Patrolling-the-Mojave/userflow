package com.hse.userflow.gateway.client;

import com.hse.userflow.dto.WorkCreateDto;
import com.hse.userflow.dto.WorkDto;
import com.hse.userflow.gateway.exception.GateWayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkStorageClient {
    private final RestTemplate restTemplate;

    @Value("${storing-service.url}")
    private String workStorageServiceUrl;

    public WorkDto createWork(WorkCreateDto newWork) {
        String url = workStorageServiceUrl + "/api/works";

        try {
            log.debug("Creating new work: {}", newWork);

            ResponseEntity<WorkDto> response = restTemplate.postForEntity(
                    url,
                    newWork,
                    WorkDto.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Work created successfully. Work ID: {}", response.getBody());
                return response.getBody();
            } else {
                throw new GateWayException("Create work failed with status: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException.BadRequest e) {
            log.error("Bad request creating work: {}", e.getResponseBodyAsString());
            throw new GateWayException("Invalid work data: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("Unexpected error creating work", e);
            throw new GateWayException("Create work failed due to unexpected error", e);
        }
    }

    public void deleteWork(Integer workId) {
        String url = workStorageServiceUrl + "/api/works/{workId}";

        try {
            log.debug("Deleting work by ID: {}", workId);

            restTemplate.delete(url, workId);
            log.info("Work deleted successfully. Work ID: {}", workId);

        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Work not found for deletion. Work ID: {}", workId);
            throw new GateWayException("Work not found with ID: " + workId);
        } catch (ResourceAccessException e) {
            log.error("Work storage service is unavailable: {}", workStorageServiceUrl, e);
            throw new GateWayException("Work storage service is unavailable", e);
        }
    }
}
