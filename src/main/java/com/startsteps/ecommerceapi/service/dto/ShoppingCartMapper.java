package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = {ProductMapper.class, CartProductMapper.class, UserMapper.class})

public interface ShoppingCartMapper extends IEntityMapper<ShoppingCartDTO, ShoppingCart>{
    @Override
    ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO);
    @Override
    List<ShoppingCartDTO> toDto(List<ShoppingCart> shoppingCartList);
}
