package com.startsteps.ecommerceapi.service.commands;

import org.springframework.stereotype.Service;

@Service
public class OrderProcessor {

    public void processOrder(OrderCommand command) {
        command.execute();
    }

}
