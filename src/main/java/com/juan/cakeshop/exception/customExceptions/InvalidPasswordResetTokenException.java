package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class InvalidPasswordResetTokenException extends BusinessException {
    public InvalidPasswordResetTokenException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
