package com.startsteps.ecommerceapi.service.dto;

import lombok.*;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Mapper(componentModel = "spring")
public class ShoppingCartDTO {
    private List<CartProductDTO> products;
    private LocalDateTime cartCreatedAt;
    private double priceTotal;
}
