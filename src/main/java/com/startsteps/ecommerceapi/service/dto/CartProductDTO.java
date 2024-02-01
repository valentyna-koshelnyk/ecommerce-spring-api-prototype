package com.startsteps.ecommerceapi.service.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class CartProductDTO {
    private ProductDTO product;
    private double totalCost;
    private Double priceProduct;
    private  long quantity;
    private ShoppingCartDTO shoppingCartDTO;
}
