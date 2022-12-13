package com.passingtest.controller;

import com.passingtest.exception.AppAuthException;
import com.passingtest.exception.AppNotFoundException;
import com.passingtest.exception.AppValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(AppValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationException(AppValidationException validationException) {
        return ErrorResponse.builder()
                .message(validationException.getMessage())
                .status(BAD_REQUEST)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(AppNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(AppNotFoundException notFoundException) {
        return ErrorResponse.builder()
                .message(notFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(AppAuthException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleNotFoundException(AppAuthException authException) {
        return ErrorResponse.builder()
                .message(authException.getMessage())
                .status(UNAUTHORIZED)
                .timestamp(now())
                .build();
    }
}
