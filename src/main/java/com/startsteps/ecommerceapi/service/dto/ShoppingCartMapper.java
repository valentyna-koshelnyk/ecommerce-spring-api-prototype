package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface ShoppingCartMapper extends IEntityMapper<ShoppingCartDTO, ShoppingCart>{
    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    @Override
    ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO);
    @Override
    ShoppingCartDTO toDto(ShoppingCart shoppingCart);

    @Override
    List<ShoppingCartDTO> listToDto(List<ShoppingCart> shoppingCartList);
}
