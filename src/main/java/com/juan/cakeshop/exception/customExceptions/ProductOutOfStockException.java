package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class ProductOutOfStockException extends BusinessException{

    public ProductOutOfStockException(int productId) {

        super("Product with id "+ productId +" out of stock!", HttpStatus.CONFLICT);
    }

}
