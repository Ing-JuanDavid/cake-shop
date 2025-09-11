package com.juan.cakeshop.exception;

import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.exception.customExceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    // Handle unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericResponse.builder()
                        .message("error")
                        .error("Unexpected error: " + ex.getMessage())
                        .build());
    }
}
