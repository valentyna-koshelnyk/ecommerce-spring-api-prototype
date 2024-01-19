package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ProductCategory;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(@NonNull String productName);
    boolean existsByProductId(long id);

    Optional<Product> findByProductId(Long aLong);
    List<Product> findProductByPriceBetween(double priceMin, double priceMax);
    List<Product> findAllByPrice(double price, Pageable pageable);
    List<Product> findProductByAddedAtDateBetween(LocalDateTime addedAtDate, LocalDateTime addedAtDate2);
    List<Product> findProductByCategory(@NonNull ProductCategory category);
    List<Product> findProductByPriceBefore(double price);
    List<Product> findProductByDescriptionContainingIgnoreCase(String str);
    @Modifying
    @Query("UPDATE Product p SET p.price = :price")
    void updateAllByPrice(@Param("price") double price);
    void deleteAllByAddedAtDate(LocalDateTime date);
    void deleteAll();
    void deleteByProductId(long id);
    void deleteByProductNameIgnoreCase(String name);
}
