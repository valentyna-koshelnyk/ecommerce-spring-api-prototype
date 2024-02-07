package com.startsteps.ecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Product not found")
public class StockCannotBeNegativeException extends RuntimeException{
    public StockCannotBeNegativeException(String msg) {
        super(msg);
    }

    public StockCannotBeNegativeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}


