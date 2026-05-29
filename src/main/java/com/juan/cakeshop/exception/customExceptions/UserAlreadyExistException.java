package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BusinessException{
    public UserAlreadyExistException(String field)
    {
        super(String.format("%s inválido", field), HttpStatus.CONFLICT);
    }
}
