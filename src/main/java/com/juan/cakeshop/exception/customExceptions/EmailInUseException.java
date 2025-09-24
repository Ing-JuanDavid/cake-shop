package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class EmailInUseException extends BusinessException{
    public EmailInUseException()
    {
        super("Invalid email, choose another email", HttpStatus.CONFLICT);
    }
}
