package com.startsteps.ecommerceapi.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CartProductDTO {
    private ProductDTO product;
    private double totalCost;
    private  long quantity;
}
