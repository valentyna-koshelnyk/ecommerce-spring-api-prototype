package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ProductMapper extends IEntityMapper<ProductDTO, Product>{
    @Override
    ProductDTO toDto(Product product);
    @Override
    Product toEntity(ProductDTO productDTO);
}
