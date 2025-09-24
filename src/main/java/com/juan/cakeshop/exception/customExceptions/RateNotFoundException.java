package com.juan.cakeshop.exception.customExceptions;

import org.springframework.http.HttpStatus;

public class RateNotFoundException extends BusinessException{
    public RateNotFoundException(int rateId)
    {
        super("No rate with rate id " + rateId, HttpStatus.NOT_FOUND);
    }
}
