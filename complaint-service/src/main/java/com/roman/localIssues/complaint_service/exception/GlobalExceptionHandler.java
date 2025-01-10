package com.roman.localIssues.complaint_service.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final LocalDateTime timestamp = LocalDateTime.now();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("Field '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .message("Validation failed for the request.")
                                .details(errors)
                                .code(HttpStatus.BAD_REQUEST.value())
                                .timestamp(timestamp)
                                .build()
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(
                        ErrorResponse.builder()
                                .message(e.getMessage())
                                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                                .timestamp(timestamp)
                                .build()
                );
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeException(HttpMediaTypeException e) {
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(
                        ErrorResponse.builder()
                                .message(e.getMessage())
                                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                                .timestamp(timestamp)
                                .build()
                );
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotWritableException(HttpMessageNotWritableException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .message(e.getMessage())
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .timestamp(timestamp)
                                .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .message("Invalid request payload. Please check the syntax and data format.")
                                .code(HttpStatus.BAD_REQUEST.value())
                                .timestamp(timestamp)
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .message(e.getMessage())
                                .code(HttpStatus.NOT_FOUND.value())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .message("Entity not found: " + e.getMessage())
                                .code(HttpStatus.NOT_FOUND.value())
                                .timestamp(timestamp)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .message("An unexpected error occurred: " + e.getMessage())
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .timestamp(timestamp)
                                .build()
                );
    }
}
