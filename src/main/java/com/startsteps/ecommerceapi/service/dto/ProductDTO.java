package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ProductCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDTO {
    private Long productId;
    private String productName;
    private double price;
    private String description;
    LocalDateTime addedAtDate;
    private Long Stock;
    private ProductCategory category;


    @Override
    public String toString(){
        if(this.getStock() < 10 && this.getStock() > 2) {
            System.out.println("Product is almost out of stock. Hurry up to order! ");
        } else if (this.getStock() <= 2 && this.getStock() > 0) {
            System.out.println("Last item");
        }
        return "Product Name: " + this.getProductName() +
                " Product Price: " + this.getPrice() +
                " Product Description: " + this.getDescription() +
                " Product Category: " + this.getCategory();
    }
}
