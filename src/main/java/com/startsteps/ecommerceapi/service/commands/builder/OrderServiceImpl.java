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


    public String printOrder(Long shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(()-> new CartNotFoundException("Cart not found"));
        return orderRepository.findOrdersByShoppingCart(shoppingCart)
                .toString();
    }

    //TODO: after canceling the order, products return back to the cart
    // add request validation from the user
    // better to put order status "canceled" and keep it for one month in a repo or create a repo with canceled orders
    public void cancelOrder(Long orderId){ // might be better to keep the order but change status to CANCELED
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        List<OrderProducts> orderProductsList = orders.getOrderItems();
        for(OrderProducts op: orderProductsList) {
            op.setOrderStatus(OrderStatus.CANCELED);
            cartService.increaseStock(op.getProductId(), op.getQuantity());
        }
        orderRepository.delete(orders);
        log.info("Order: " + orders + " was cancelled");
    }

    //TODO: to adjust order history
    public List<OrderProducts> getOrderHistory(Long shoppingCartId){
        return orderProductRepository.findByShoppingCartId(shoppingCartId);
    }



}