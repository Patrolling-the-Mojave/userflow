package com.hse.userflow.storingservice.handler;

import com.hse.userflow.dto.error.ErrorResponse;
import com.hse.userflow.storingservice.exception.FileDownloadException;
import com.hse.userflow.storingservice.exception.FileUploadException;
import com.hse.userflow.storingservice.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleFileDownload(final FileDownloadException exception) {
        log.error("file download exception", exception);
        return new ErrorResponse("file download", exception.getMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleFileUpload(FileUploadException exception) {
        log.error("file upload exception", exception);
        return new ErrorResponse("file upload", exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException exception) {
        log.error("not found", exception);
        return new ErrorResponse("not found", exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception exception) {
        log.error("exception", exception);
        return new ErrorResponse("exception", exception.getMessage());
    }
}
