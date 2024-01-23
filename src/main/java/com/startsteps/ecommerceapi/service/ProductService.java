package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(ProductDTO product);
    List<Product> findProductInPriceRange(double priceMin, double priceMax);
    List<Product> findAllSortedByPrice(Pageable pageable);
    void deleteProductById(long id);
    void deleteByProductByName(String name);
    List<Product> findAllSortedByDate();
    void deleteAllByAddedAtDate(LocalDateTime date);
    Optional<Product> updateProductByPrice(long id, double price);








}
