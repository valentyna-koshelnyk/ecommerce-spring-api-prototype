package com.startsteps.ecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Product not found")
public class ProductNotFoundException extends RuntimeException {
     public ProductNotFoundException(String msg) {
         super(msg);
     }

    public ProductNotFoundException(String msg, Throwable cause) {
         super(msg, cause);
     }
}
