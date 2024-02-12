package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CartProductDTO {
    private ProductDTO product;
    private double totalCost;
    private Double priceProduct;
    private  long quantity;
    private ShoppingCart shoppingCart;

    @Override
    public String toString(){
        return "Product: " + this.product.getProductName() +
                "Product price: " + this.product.getPrice() +
                "Quantity: " + this.quantity +
                "Total per Product: " + this.priceProduct;
    }

}
