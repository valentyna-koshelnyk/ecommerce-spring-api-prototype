package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.payload.response.ProductResponse;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    ProductResponse findAllProducts(PageRequest pageable);

}