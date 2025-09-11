package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class InvalidProductImageException extends  BusinessException{
    public InvalidProductImageException() {
        super("Product's image must be provided", HttpStatus.BAD_REQUEST);
    }
}
