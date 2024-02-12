package com.startsteps.ecommerceapi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCartDTO {

    @JsonProperty("Price total: ")
    private Double priceTotal;
}
