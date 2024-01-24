package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Product;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByProductName(@NonNull String productName);

    void deleteProductByProductName(String name);
    Page<Product> findAllByStockGreaterThanEqual(int amount, Pageable pageable);

}
