package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.InsufficientStockException;
import com.startsteps.ecommerceapi.exceptions.ProductNotFoundException;
import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.payload.request.ProductAddRequest;
import com.startsteps.ecommerceapi.persistence.CartProductRepository;
import com.startsteps.ecommerceapi.persistence.ProductRepository;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.persistence.UserRepository;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import com.startsteps.ecommerceapi.service.dto.ProductMapper;
import com.startsteps.ecommerceapi.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CartServiceImpl implements CartService{

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;

    private ProductDTO product;
    private UserDTO user;
    private ProductServiceImpl productService;
    private  ProductMapper productMapper;
    @Autowired
    public CartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, CartProductRepository cartProductRepository, UserRepository userRepository, ProductServiceImpl productService, ProductMapper productMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }
   @Override
   public void addProductToCart(ProductAddRequest request) {
       Product product = productRepository.findProductByProductId(request.getProductId())
               .orElseThrow(() -> new ProductNotFoundException("Product with ID " + request.getProductId() + " not found."));
       ShoppingCart cart = shoppingCartRepository.findShoppingCartByCartId(request.getCartId());
        boolean isProductInCart = isProductInUserCart(request.getProductId(), request.getCartId());

        if (isProductInCart) {
            CartProduct cartProduct = cartProductRepository.findCartProductByProductAndShoppingCart(product, cart)
                    .orElseThrow();
            cartProduct.setQuantity(cartProduct.getQuantity() + request.getQuantity());
            updateTotalCost(cartProduct, product, request.getQuantity());

        } else {
            addCartProduct(product, cart, request.getQuantity());
        }
        reduceProductStock(request.getQuantity(), request.getProductId());
    }
   @Override
   public void reduceProductStock(Long quantity, Long productId) {
        Product product = productRepository.findProductByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product ID " + productId);
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
    @Override
    public void addCartProduct(Product product, ShoppingCart cart, Long quantity){
        CartProduct newCartProduct = new CartProduct(product, cart, quantity);
        newCartProduct.setTotalCost(calculateProductCost(product, quantity));
        cartProductRepository.save(newCartProduct);
    }

    public double calculateProductCost(Product product, Long quantity){
        return product.getPrice() * quantity;
    }

    public double updateTotalCost(CartProduct cartProduct,
                                  Product product,
                                  Long quantity){
        return cartProduct.getTotalCost() + calculateProductCost(product, quantity);

    }

    @Override
    public boolean isProductInUserCart(Long productId, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId);
        Product product = productRepository.findProductByProductId(productId).orElseThrow();
       return cartProductRepository.findCartProductByProductAndShoppingCart(product, shoppingCart)
                .isPresent();
    }

//    public List<CartProduct> viewProductsInCart(Long cartId){
//
//
//    }
    public void clearCart(){
    }
    public void removeProductFromCart(){
    }
}

