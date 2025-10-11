package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends BusinessException{
    public InvalidInputException(String input){
        super("Invalid " + input, HttpStatus.BAD_REQUEST);
    }
}
