package com.hse.userflow.gateway.exception;

import com.hse.userflow.dto.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@ToString
public class GateWayException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorResponse errorResponse;

}
