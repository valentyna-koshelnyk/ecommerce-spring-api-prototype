package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private double price;
    private String description;
    private long stock;
    LocalDateTime addedAtDate;
    private ProductCategory category;

}
