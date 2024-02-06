package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ProductCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductDTO {
    private Long productId;
    private String productName;
    private double price;
    private String description;
    LocalDateTime addedAtDate;
    private Long Stock;
    private ProductCategory category;

    public ProductDTO(String productName, double price, String description) {
        this.productName = productName;
        this.price = price;
        this.description = description;
    }
}
