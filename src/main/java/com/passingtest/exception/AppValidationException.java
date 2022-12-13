package com.passingtest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppValidationException extends Exception {
    private final String message;
}
