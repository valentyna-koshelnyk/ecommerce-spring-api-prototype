package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.CartProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartProductMapper extends IEntityMapper<CartProductDTO, CartProduct>{

    @Override
    CartProduct toEntity(CartProductDTO productDTO);
    @Override
    List<CartProductDTO> toDto(List<CartProduct> cartProductList);
}