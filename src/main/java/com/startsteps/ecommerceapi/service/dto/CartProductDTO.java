package com.startsteps.ecommerceapi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartProductDTO {
    private ProductDTO product;
    @JsonIgnore
    private Double priceProduct;
    private  Long quantity;
    private ShoppingCartDTO shoppingCart;

    @Override
    public String toString(){
        return "  *************" +
                "| Product: " + this.product.getProductName() +
                " | Product price: " + this.product.getPrice() +
                " | Quantity: " + this.quantity +
                " | Total price per product: " + this.priceProduct +
                " | Total price for order: " + this.shoppingCart.getPriceTotal();
    }

}
