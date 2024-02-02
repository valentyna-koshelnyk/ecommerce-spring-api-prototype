package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.CartProduct;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartProductMapper extends IEntityMapper<CartProductDTO, CartProduct> {

    @Override
    CartProduct toEntity(CartProductDTO productDTO);

    @Override
    CartProductDTO toDto(CartProduct cartProduct);

    @Override
    List<CartProductDTO> toDto(List<CartProduct> cartProductList);

    default Page<CartProductDTO> toDtoPage(Page<CartProduct> entitiesPage) {
        List<CartProductDTO> dtoList = entitiesPage.getContent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, entitiesPage.getPageable(), entitiesPage.getTotalElements());
    }
}