package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ProductCategory;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NonNull
    @NotBlank
    private String productName;
    @NonNull
    private double price;
    @NonNull
    private String description;
    @NonNull
    private long stock;
    @NonNull
    private ProductCategory productCategory;
    private LocalDateTime createdDate;

    public ProductDTO(String productName, double price, String description, long stock, ProductCategory productCategory) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.productCategory = productCategory;
    }
}
