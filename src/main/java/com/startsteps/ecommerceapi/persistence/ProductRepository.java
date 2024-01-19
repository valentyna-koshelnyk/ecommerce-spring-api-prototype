package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ProductCategory;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long aLong);
    List<Product> findProductByPriceBetween(double priceMin, double priceMax);
    List<Product> findAllByPrice(double price, Pageable pageable);
    List<Product> findProductByAddedAtDateBetween(LocalDateTime addedAtDate, LocalDateTime addedAtDate2);
    List<Product> findProductByCategory(@NonNull ProductCategory category);
    List<Product> findProductByPriceBefore(double price);
    List<Product> findProductByDescriptionContainingIgnoreCase(String str);
    Product updateAllByPrice(double price);
    Product updateProductByDescriptionContaining(String description);
    void deleteAllByAddedAtDate(LocalDateTime date);
    void deleteAll();
    void deleteByProductId(long id);
    void deleteByProductNameIgnoreCase(String name);






}
