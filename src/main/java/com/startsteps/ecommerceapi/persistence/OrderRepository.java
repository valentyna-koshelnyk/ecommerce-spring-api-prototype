package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Orders;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Orders, Long> {

}