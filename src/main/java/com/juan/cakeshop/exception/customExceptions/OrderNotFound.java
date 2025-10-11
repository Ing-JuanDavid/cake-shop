package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class OrderNotFound extends BusinessException {
    public OrderNotFound(){
        super("Order not found", HttpStatus.NOT_FOUND);
    }
}
