package com.juan.cakeshop.exception;

import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.exception.customExceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handler authentication errors
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GenericResponse<Object>> handleAuthException(AuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.builder()
                        .message("error")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<GenericResponse<Object>> handleAuthException(AuthorizationDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(GenericResponse.builder()
                        .message("error")
                        .error(ex.getMessage())
                        .build());
    }

    // Handle validations errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", "error",
                        "error", errors
                ));
    }

    // Handle business errors
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GenericResponse<Object>> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(GenericResponse.builder()
                        .message("error")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GenericResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(GenericResponse.builder()
                        .message("error")
                        .error(ex.getReason())
                        .build());
    }

    // Handle dto errors

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse<Object>> handleHttpMessageNotReadableException(Exception ex)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.<Object>builder()
                        .message("error")
                        .error("Unexpected value")
                        .build());
    }

    // Handle unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericResponse.builder()
                        .message("error")
                        .error("Unexpected error: " + ex.getCause())
                        .build());
    }
}
