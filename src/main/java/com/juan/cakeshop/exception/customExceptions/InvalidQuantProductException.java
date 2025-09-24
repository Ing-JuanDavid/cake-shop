package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class InvalidQuantProductException extends BusinessException{
    public InvalidQuantProductException(int quant) {
        super("Invalid quantity " + quant +". Product's quantity must be grater than 0", HttpStatus.BAD_REQUEST);
    }
}
