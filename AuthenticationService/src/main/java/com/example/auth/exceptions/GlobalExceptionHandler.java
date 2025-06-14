package com.example.auth.exceptions;

import com.example.auth.model.response.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        log.error("InvalidPasswordException: {}", ex.getMessage());
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header("InvalidPassword")
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);

    }
}
