package com.startsteps.ecommerceapi.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddRequest {
    private Long productId;
    private Long quantity;
}
