package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByProductName(@NonNull String productName);

    void deleteProductByProductName(String name);
    Product findProductByProductId(long productId);
    Page<Product> findAllByStockGreaterThanEqual(int amount, Pageable pageable);
    Optional<Product> findProductByProductIdAndStockGreaterThanEqual(int amount, Long id);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')) AND p.stock > :stock")
    List<Product> findAllByProductNameContainingIgnoreCaseAndStockGreaterThan(@Param("stock") long stock, @Param("name") String name);
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')) AND p.stock > :stock")

    Optional<ProductDTO> findProductByProductNameContainingIgnoreCase(@Param("stock") long stock, @Param("name") String name);
}
