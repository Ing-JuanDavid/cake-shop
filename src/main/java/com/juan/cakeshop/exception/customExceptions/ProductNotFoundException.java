package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessException{

    public ProductNotFoundException(int productId)
    {
        super("Product with id " + productId + " not found", HttpStatus.NOT_FOUND);
    }
}
