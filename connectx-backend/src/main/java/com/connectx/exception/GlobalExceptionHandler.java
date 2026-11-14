package com.connectx.exception;

import com.connectx.dto.response.ApiResponse;
import com.connectx.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiResponse createError(
            HttpStatus status,
            String message,
            String path,
            Map<String, String> errors
    ) {
        return new ApiResponse(
                false,
                status.value(),
                message,
                path,
                Instant.now(),
                errors
        );
    }

    // 404 - User not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(status)
                .body(createError(
                        status,
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                ));
    }

    // 409 - Duplicate email or username
    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            UsernameAlreadyExistsException.class
    })
    public ResponseEntity<ApiResponse> handleConflict(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity
                .status(status)
                .body(createError(
                        status,
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                ));
    }

    // 400 - Validation failure
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(createError(
                        status,
                        "Validation failed",
                        request.getRequestURI(),
                        errors
                ));
    }

    // 500 - Unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpectedError(
            Exception ex,
            HttpServletRequest request
    ) {
        // Never expose internal exception details to API clients.
        // Production logging will be added in our observability work.

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(createError(
                        status,
                        "An unexpected error occurred",
                        request.getRequestURI(),
                        null
                ));
    }
}