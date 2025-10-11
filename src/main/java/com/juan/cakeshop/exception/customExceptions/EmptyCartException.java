package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class EmptyCartException extends BusinessException{
    public EmptyCartException(String message)
    {
        super(message, HttpStatus.CONFLICT);
    }
}
