package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class InvalidProductIdException extends BusinessException{
    public InvalidProductIdException() {
        super("Product id must be greater than 0", HttpStatus.BAD_REQUEST);
    }
}
