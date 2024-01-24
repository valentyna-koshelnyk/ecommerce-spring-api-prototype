package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.payload.request.SearchCriteria;
import com.startsteps.ecommerceapi.payload.response.ProductResponse;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    ProductResponse findAllProducts(PageRequest pageable);
    Product addProduct(ProductDTO productDTO);
    void deleteProductByCriteria(SearchCriteria searchCriteria);

    void updateProductByCriteria(SearchCriteria searchCriteria, ProductDTO product);
}