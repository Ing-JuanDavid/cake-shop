package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BusinessException{
    public UserAlreadyExistException(String field, String value)
    {
        super(String.format("Already exist an user with %s: %s", field, value), HttpStatus.CONFLICT);
    }
}
