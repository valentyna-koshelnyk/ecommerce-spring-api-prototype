package com.startsteps.ecommerceapi.user.service.dto;

import jakarta.persistence.Column;
import lombok.NonNull;

public class ProductDTO {
    private long productId;
    private String productName;
    private double price;
    private String description;
    private long stock;
}
