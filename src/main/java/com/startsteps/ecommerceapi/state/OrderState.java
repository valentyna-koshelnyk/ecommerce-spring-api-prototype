package com.startsteps.ecommerceapi.state;

import com.startsteps.ecommerceapi.model.Orders;
import org.springframework.stereotype.Service;

@Service
public interface OrderState {
    void next(Orders order);
    void prev(Orders order);
    void printStatus(Orders orders);
}