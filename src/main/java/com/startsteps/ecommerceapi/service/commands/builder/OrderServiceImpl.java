package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.exceptions.CartNotFoundException;
import com.startsteps.ecommerceapi.exceptions.OrderNotFoundException;
import com.startsteps.ecommerceapi.model.*;
import com.startsteps.ecommerceapi.persistence.*;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import com.startsteps.ecommerceapi.service.OrderProductsServiceImpl;
import com.startsteps.ecommerceapi.service.commands.OrderCommand;
import com.startsteps.ecommerceapi.service.commands.OrderProcessor;
import com.startsteps.ecommerceapi.service.commands.PlaceOrderCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProcessor orderProcessor;
    private final OrderBuilder orderBuilder;
    private final CartServiceImpl cartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderValidatorImpl orderValidator;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private OrderProducts orderProducts;
    private final OrderProductsServiceImpl orderProductsService;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderProcessor orderProcessor, OrderBuilder orderBuilder,
                            ShoppingCartRepository shoppingCartRepository, CartServiceImpl cartService,
                            UserRepository userRepository, OrderValidatorImpl orderValidator,
                            CartProductRepository cartProductRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository, OrderProductsServiceImpl orderProductsService) {
        this.orderRepository = orderRepository;
        this.orderProcessor = orderProcessor;
        this.orderBuilder = orderBuilder;
        this.cartService = cartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.orderValidator = orderValidator;
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderProductsService = orderProductsService;
    }
    public void saveOrder(Orders order) {
        orderRepository.save(order);
        List<CartProduct> cartProducts = cartProductRepository.findCartProductByShoppingCart(order.getShoppingCart());
        orderProductsService.addProductFromCart(cartProducts);
        cartService.emptyCart(order.getShoppingCart().getCartId());

    }
    @Transactional
    public void placeOrder(Long shoppingCartId) {
        ShoppingCart shoppingCart = cartService.findShoppingCartByCartId(shoppingCartId);
        if (shoppingCart == null) {
            throw new CartNotFoundException("No cart found with id: " + shoppingCartId);
        }
        UserInformation userInformation = shoppingCart.getUser().getUserInformation();
        log.debug("Building order with ShoppingCart id: {}", shoppingCartId);
        OrderBuilder builder = new OrderBuilder()
                .shoppingCart(shoppingCart)
                .information(userInformation)
                .orderValidator(orderValidator)
                .orderService(this)
                .shoppingCart(shoppingCart)
                .shoppingCartRepository(shoppingCartRepository)
                .userRepository(userRepository)
                .user(shoppingCart.getUser());
        OrderCommand placeOrderCommand = new PlaceOrderCommand(builder);
        orderProcessor.processOrder(placeOrderCommand);
    }

    @Transactional
    public String printOrder(Long shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(()-> new CartNotFoundException("Cart not found"));
        List<Orders> orders = orderRepository.findOrdersByShoppingCartOrderByOrderCreatedAtDesc(shoppingCart);

        if(orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for this cart");
        }
        Orders lastOrder = orders.get(0);
        return lastOrder.toString();
    }

    //TODO: after canceling the order, products return back to the cart
    // add request validation from the user
    // better to put order status "canceled" and keep it for one month in a repo or create a repo with canceled orders
    public void cancelOrder(Long orderId){ // might be better to keep the order but change status to CANCELED
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        List<OrderProducts> orderProductsList = orderProductRepository.findByOrderId(orderId);
        for(OrderProducts op: orderProductsList) {
            op.setOrderStatus(OrderStatus.CANCELED);
            cartService.increaseStock(op.getProductId(), op.getQuantity());
            orderProductRepository.save(op);
        }

        orderRepository.delete(orders);
        log.info("Order: " + orders + " was cancelled");
    }


    @Transactional
    public Orders moveToNextState(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(()
                -> new OrderNotFoundException("Order not found"));
        order.nextState();
        orderRepository.save(order);
        order.printState();
        if(order.getOrderStatus() == OrderStatus.DELIVERED){
            List<OrderProducts> orderProductsList = orderProductRepository.findByOrderId(orderId);
            for(OrderProducts op : orderProductsList){
                op.setOrderStatus(OrderStatus.DELIVERED);
            }
        }
        return order;
    }

    @Override
    public  Orders previousState(Long orderId){
        Orders order = orderRepository.findById(orderId).orElseThrow(()
                -> new OrderNotFoundException("Order not found"));
        order.previousState();
        orderRepository.save(order);
        order.printState();
        return order;
    }
    @Override
    public void checkOrderStatus(Long orderId){
        Orders order = orderRepository.findById(orderId).orElseThrow(()
                -> new OrderNotFoundException("Order not found"));
         order.printState();
    }

    //TODO: to adjust order history
    public List<OrderProducts> getOrderHistory(Long shoppingCartId){
        List<OrderProducts> orderProductsList =  orderProductRepository.findByShoppingCartIdAndOrderStatus(shoppingCartId, OrderStatus.DELIVERED);
        if(orderProductsList.isEmpty()){
            throw new OrderNotFoundException
                    ("There are no delivered orders for your account. " +
                            "For the current order check its status.");
        }
        return orderProductsList;
    }

}