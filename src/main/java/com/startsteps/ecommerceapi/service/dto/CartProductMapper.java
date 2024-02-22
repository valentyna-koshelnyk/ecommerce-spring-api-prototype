package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.CartProduct;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartProductMapper extends IEntityMapper<CartProductDTO, CartProduct> {
    CartProductMapper INSTANCE = Mappers.getMapper(CartProductMapper.class);

    @Override
    CartProduct toEntity(CartProductDTO productDTO);

    @Override
    CartProductDTO toDto(CartProduct cartProduct);
    @Override
    List<CartProductDTO> listToDto(List<CartProduct> cartProducts);

}