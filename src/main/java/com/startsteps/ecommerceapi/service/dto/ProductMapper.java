package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper extends IEntityMapper<ProductDTO, Product>{
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Override
    ProductDTO toDto(Product product);
    @Override
    Product toEntity(ProductDTO productDTO);
    default Page<ProductDTO> toDtoPage(Page<Product> entitiesPage) {
        List<ProductDTO> dtoList = entitiesPage.getContent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, entitiesPage.getPageable(), entitiesPage.getTotalElements());
    }
}
