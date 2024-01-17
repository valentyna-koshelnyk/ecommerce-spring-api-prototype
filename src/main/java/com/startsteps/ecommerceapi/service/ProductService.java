package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.Product;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> findProductInPriceRange(double priceMin, double priceMax);
    List<Product> findAllSortedByPrice(double price, Pageable pageable);
    void deleteProductById(long id);
    void deleteByProductByName(String name);
    List<Product> findAllSortedByDate();
    void deleteAllByAddedAtDate(LocalDateTime date);
    Optional<Product> updateProductByPrice(long id, double price);








}