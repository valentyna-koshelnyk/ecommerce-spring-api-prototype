package com.startsteps.ecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Product with this name already exists")
public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String msg) {
        super(msg);
    }
}
