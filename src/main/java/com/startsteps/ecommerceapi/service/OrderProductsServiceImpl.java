package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.OrderProducts;
import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.persistence.OrderProductRepository;
import com.startsteps.ecommerceapi.persistence.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class OrderProductsServiceImpl {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

   @Autowired
   public OrderProductsServiceImpl(OrderProductRepository orderProductRepository, OrderRepository orderRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addProductFromCart(List<CartProduct> cartProducts, Orders orders) {
        List<OrderProducts> orderProducts = new ArrayList<>();
        for (CartProduct cp : cartProducts) {
            orderProducts.add(new OrderProducts(cp, orders));
        }
        orderProductRepository.saveAll(orderProducts);
    }

}
