package com.example.user.exceptions;

import com.example.user.model.response.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * al exception handler class for handling exceptions thrown by the application.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "UserNotFound");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorResponse> handleBadRequestException(BadRequestException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "BadRequest");
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "BadRequest");
    }
    
    private ResponseEntity<CustomErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String errorType) {
        log.error("{}: {}", errorType, ex.getMessage());
        CustomErrorResponse errorResponse = new CustomErrorResponse(errorType, ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }


}
