package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class EmptyCartException extends BusinessException{
    public EmptyCartException()
    {
        super("Something happened to empty cart", HttpStatus.CONFLICT);
    }
}
