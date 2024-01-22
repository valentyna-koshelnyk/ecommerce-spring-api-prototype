package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ProductCategory;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long productId;
    private String productName;
    private double price;
    private String description;
    private long stock;
    private ProductCategory productCategory;
    private LocalDateTime createdDate;

    public ProductDTO(long productId, String productName, double price, String description, long stock, ProductCategory productCategory) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.productCategory = productCategory;
    }
}
