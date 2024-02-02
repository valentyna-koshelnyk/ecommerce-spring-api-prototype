package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    void delete(Orders entity);
}