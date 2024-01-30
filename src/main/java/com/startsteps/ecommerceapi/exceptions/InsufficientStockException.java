package com.startsteps.ecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Not enough items in stock")
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String msg){
        super(msg);
    }
}
