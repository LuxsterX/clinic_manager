package com.example.clinicmanager.exceptions;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler to manage and handle exceptions across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException and returns a 400 Bad Request response.
     *
     * @param ex the IllegalArgumentException thrown by the application
     * @return ResponseEntity containing the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request - Illegal Argument Exception"),
    })
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles generic exceptions and returns a 500 Internal Server Error response.
     *
     * @param ex the generic exception thrown by the application
     * @return ResponseEntity containing a generic error message
     */
    @ExceptionHandler(Exception.class)
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Unexpected Error"),
    })
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
