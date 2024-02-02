package com.startsteps.ecommerceapi.payload.request;

import com.startsteps.ecommerceapi.service.dto.ProductDTO;

public class ViewCartRequest {
    private ProductDTO product;
    private Double totalCost;
    private Double priceProduct;
    private  Long quantity;


}
