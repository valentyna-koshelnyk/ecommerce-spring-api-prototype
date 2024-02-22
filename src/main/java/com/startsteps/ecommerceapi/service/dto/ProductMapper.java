package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper extends IEntityMapper<ProductDTO, Product>{
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Override
    ProductDTO toDto(Product product);
    @Override
    Product toEntity(ProductDTO productDTO);
    @Override
    List<ProductDTO> listToDto(List<Product> productList);
}
