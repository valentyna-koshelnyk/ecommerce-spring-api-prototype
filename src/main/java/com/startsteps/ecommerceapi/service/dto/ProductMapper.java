package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.Product;

public interface ProductMapper extends IEntityMapper<ProductDTO, Product>{

    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
}
