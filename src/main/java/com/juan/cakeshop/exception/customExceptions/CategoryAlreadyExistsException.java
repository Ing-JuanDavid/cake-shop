package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistsException extends BusinessException{
    public CategoryAlreadyExistsException() {
        super("Ya existe una categoria con ese nombre", HttpStatus.BAD_REQUEST);
    }
}
