package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface ShoppingCartMapper extends IEntityMapper<ShoppingCartDTO, ShoppingCart>{
    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    @Override
    ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO);
    @Override
    ShoppingCartDTO toDto(ShoppingCart shoppingCart);

    @Override
    default Page<ShoppingCartDTO> toDtoPage(Page<ShoppingCart> entitiesPage) {
        List<ShoppingCartDTO> dtoList = entitiesPage.getContent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, entitiesPage.getPageable(), entitiesPage.getTotalElements());
    }
}
