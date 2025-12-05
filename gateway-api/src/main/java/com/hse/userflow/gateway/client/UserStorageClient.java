package com.hse.userflow.gateway.client;
import com.hse.userflow.dto.UserCreateDto;
import com.hse.userflow.dto.UserDto;
import com.hse.userflow.gateway.exception.GateWayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserStorageClient {
    private final RestTemplate restTemplate;

    @Value("${storing-service.url")
    private String userStorageServiceUrl;

    public UserDto createUser(UserCreateDto newUser) {
        String url = userStorageServiceUrl + "/api/users";

        try {
            log.debug("Creating new user: {}", newUser);

            ResponseEntity<UserDto> response = restTemplate.postForEntity(
                    url,
                    newUser,
                    UserDto.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("User created successfully. User ID: {}", response.getBody());
                return response.getBody();
            } else {
                throw new GateWayException("Create user failed with status: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException.BadRequest e) {
            log.error("Bad request creating user: {}", e.getResponseBodyAsString());
            throw new GateWayException("Invalid user data: " + e.getResponseBodyAsString(), e);
        } catch (HttpClientErrorException e) {
            log.error("HTTP error creating user. Status: {}", e.getStatusCode());
            throw new GateWayException("Create user failed: " + e.getStatusCode(), e);
        } catch (ResourceAccessException e) {
            log.error("User storage service is unavailable: {}", userStorageServiceUrl, e);
            throw new GateWayException("User storage service is unavailable", e);
        } catch (Exception e) {
            log.error("Unexpected error creating user", e);
            throw new GateWayException("Create user failed due to unexpected error", e);
        }
    }

    /**
     * Получение пользователя по ID
     */
    public UserDto getUserById(Integer userId) {
        String url = userStorageServiceUrl + "/api/users/{userId}";

        try {
            log.debug("Getting user by ID: {}", userId);

            ResponseEntity<UserDto> response = restTemplate.getForEntity(
                    url,
                    UserDto.class,
                    userId
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new GateWayException("Get user failed with status: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException.NotFound e) {
            log.error("User not found with ID: {}", userId);
            throw new GateWayException("User not found with ID: " + userId);
        } catch (ResourceAccessException e) {
            log.error("User storage service is unavailable: {}", userStorageServiceUrl, e);
            throw new GateWayException("User storage service is unavailable", e);
        } catch (Exception e) {
            log.error("Error getting user with ID: {}", userId, e);
            throw new GateWayException("Get user failed for ID: " + userId, e);
        }
    }

    public void deleteUser(Integer userId) {
        String url = userStorageServiceUrl + "/api/users/{userId}";

        try {
            log.debug("Deleting user by ID: {}", userId);

            restTemplate.delete(url, userId);
            log.info("User deleted successfully. User ID: {}", userId);

        } catch (HttpClientErrorException.NotFound e) {
            log.warn("User not found for deletion. User ID: {}", userId);
            throw new GateWayException("User not found with ID: " + userId);
        } catch (HttpClientErrorException e) {
            log.error("HTTP error deleting user. Status: {}", e.getStatusCode());
            throw new GateWayException("Delete user failed: " + e.getStatusCode(), e);
        } catch (ResourceAccessException e) {
            log.error("User storage service is unavailable: {}", userStorageServiceUrl, e);
            throw new GateWayException("User storage service is unavailable", e);
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", userId, e);
            throw new GateWayException("Delete failed for user: " + userId, e);
        }
    }
}
