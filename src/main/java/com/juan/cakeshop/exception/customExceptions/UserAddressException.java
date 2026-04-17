package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class UserAddressException extends BusinessException {
    public UserAddressException(String message, HttpStatus status) {
        super(message, status);
    }
}
