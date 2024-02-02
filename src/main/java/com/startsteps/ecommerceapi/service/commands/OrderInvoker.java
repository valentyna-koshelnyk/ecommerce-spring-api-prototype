package com.startsteps.ecommerceapi.service.commands;

public class OrderInvoker {
    private OrderCommand orderCommand;


    public OrderInvoker(OrderCommand orderCommand) {
        this.orderCommand = orderCommand;
    }

    public void executeCommand(){
        this.orderCommand.execute();
    }

    public void unexecuteCommand(){
        this.orderCommand.unexecute();
    }
}
