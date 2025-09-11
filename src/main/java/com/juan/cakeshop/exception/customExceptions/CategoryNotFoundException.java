package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BusinessException{

    public CategoryNotFoundException(int id)
    {
        super("Category with id " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
