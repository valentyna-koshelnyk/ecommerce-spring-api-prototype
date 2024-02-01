package com.startsteps.ecommerceapi.service.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ShoppingCartDTO {
    private List<CartProductDTO> products;
    private double priceTotal;
}
