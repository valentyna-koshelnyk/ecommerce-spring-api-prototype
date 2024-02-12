package com.startsteps.ecommerceapi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startsteps.ecommerceapi.model.ProductCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    @NonNull
    private String productName;
    @NonNull
    private Double price;
    @NonNull
    private String description;
    @NonNull
    private Long stock;

    public void setAddedAtDate(LocalDateTime addedAtDate) {
        this.addedAtDate = LocalDateTime.now();
    }
    @DateTimeFormat
    @JsonIgnore
    private LocalDateTime addedAtDate;
    @NonNull
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Override
    public String toString(){
        return "Product Name: " + this.getProductName() +
                " Product Price: " + this.getPrice() +
                " Product Description: " + this.getDescription() +
                " Product Category: " + this.getCategory();
    }
}
