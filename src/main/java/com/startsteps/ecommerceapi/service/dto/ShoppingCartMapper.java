package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")

public interface ShoppingCartMapper extends IEntityMapper<ShoppingCartDTO, ShoppingCart>{
    @Override
    ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO);
    @Override
    @Mapping(target = "stock", ignore = true)
    @Mapping(source = "products", target = "productDto")
    List<ShoppingCartDTO> toDto(List<ShoppingCart> shoppingCartList);
}
