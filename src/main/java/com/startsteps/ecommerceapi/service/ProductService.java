package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(ProductDTO product);
    List<Product> findProductInPriceRange(double priceMin, double priceMax);
    List<Product> findAll();

    Page<Product> findProductByProductNameContainingIgnoreCase(String name, Pageable paging);
    Page<Product> findAll(Pageable pageable);

    Page<Product> findProductsWithSorting(int offset, int pageSize, String field);
    void deleteProductById(long id);
    void deleteByProductByName(String name);
    void deleteAllByAddedAtDate(LocalDateTime date);
    Product updateProductByPrice(long id, double price);
    Product findByProductId(Long aLong);

}
