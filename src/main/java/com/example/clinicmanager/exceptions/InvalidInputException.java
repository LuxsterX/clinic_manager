package com.example.clinicmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when there is invalid input provided (e.g., duplicate username or email).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)  // Return HTTP 400 status code
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
