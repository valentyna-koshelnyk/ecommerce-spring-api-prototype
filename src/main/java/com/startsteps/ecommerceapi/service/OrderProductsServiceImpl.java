package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.OrderProducts;
import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.persistence.OrderProductRepository;
import com.startsteps.ecommerceapi.persistence.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class OrderProductsServiceImpl {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;



    public OrderProductsServiceImpl(OrderProductRepository orderProductRepository, OrderRepository orderRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addProductFromCart(List<CartProduct> cartProduct) {
        List<OrderProducts> orderProductList = new ArrayList<>();
        for(CartProduct cp: cartProduct){
            Orders orders = orderRepository.findOrdersByShoppingCart(cp.getShoppingCart());
            OrderProducts orderProduct = new OrderProducts();
            orderProduct.setProductName(cp.getProduct().getProductName());
            orderProduct.setQuantity(cp.getQuantity());
            orderProduct.setPriceProduct(cp.getPriceProduct());
            orderProduct.setShoppingCartId(cp.getShoppingCart().getCartId());
            orderProduct.setOrderCreatedAt(LocalDateTime.now());
            orderProduct.setProductId(cp.getProduct().getProductId());
            orderProduct.setOrderId(orders.getOrderId());
            orderProduct.setOrderStatus(orders.getOrderStatus());
            orderProductRepository.save(orderProduct);
        }
    }

}
