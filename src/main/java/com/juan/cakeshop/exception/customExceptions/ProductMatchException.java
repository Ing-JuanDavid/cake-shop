package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class ProductMatchException extends BusinessException {
    public ProductMatchException (int productId) {
        super("Product id " + productId + " don't match with original product", HttpStatus.CONFLICT);
    }
}
