package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.payload.response.ProductResponse;
import com.startsteps.ecommerceapi.persistence.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl{

    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public ProductResponse findAllProducts(PageRequest pageable) {
        var productsPage= this.productRepository.findAll(pageable);
        return buildResponse(productsPage);
    }
    private ProductResponse buildResponse(Page productsPage){
        return ProductResponse.builder()
                .pageNumber(productsPage.getNumber() + 1)
                .pageSize(productsPage.getSize())
                .totalElements(productsPage.getTotalElements())
                .totalPages(productsPage.getTotalPages())
                .products(productsPage.toList())
                .isLastPage(productsPage.isLast())
                .build();
    }
}
