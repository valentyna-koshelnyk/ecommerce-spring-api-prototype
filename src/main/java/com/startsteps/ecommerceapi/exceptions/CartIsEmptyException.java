package com.startsteps.ecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Cart is empty, nothing to show")
public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException() {
        super("The shopping cart is empty.");
    }

    public CartIsEmptyException(String message) {
        super(message);
    }
}
