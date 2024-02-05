package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(componentModel = "spring", uses = {CartProductMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface ShoppingCartMapper extends IEntityMapper<ShoppingCartDTO, ShoppingCart>{
    @Override
    ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO);

}
