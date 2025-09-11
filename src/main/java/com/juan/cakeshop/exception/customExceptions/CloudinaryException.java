package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class CloudinaryException extends BusinessException{

    public CloudinaryException(String message, HttpStatus status) {
        super(message, status);
    }
}
