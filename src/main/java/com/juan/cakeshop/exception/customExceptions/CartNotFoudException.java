package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class CartNotFoudException extends BusinessException{
    public CartNotFoudException() {
        super("Cart not found", HttpStatus.NOT_FOUND);
    }
}
