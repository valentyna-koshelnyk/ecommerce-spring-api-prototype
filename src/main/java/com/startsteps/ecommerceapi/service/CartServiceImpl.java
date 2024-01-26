package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.InsufficientStockException;
import com.startsteps.ecommerceapi.exceptions.ProductNotFoundException;
import com.startsteps.ecommerceapi.exceptions.UserNotFoundException;
import com.startsteps.ecommerceapi.service.dto.CartItem;
import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
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
    private final long ITEM = 1;
    @Autowired
    public CartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, CartProductRepository cartProductRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.userRepository = userRepository;
    }
   @Override
   public void addProductToCart(Long userId, Long productId, int quantity) {
        CartItem cartItem = createCartItem(userId, productId, quantity);
        ProductDTO product = cartItem.product();
        ShoppingCart cart = cartItem.shoppingCart();

        boolean isProductInCart = isProductInUserCart(productId, userId);

        if (isProductInCart) {
            CartProduct cartProduct = cartProductRepository.findCartProductByProductAndShoppingCart(product, cart);
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
            cartProductRepository.save(cartProduct);

        } else {
            addCartProduct(product, cart, quantity);
        }
        reduceProductStock(quantity, productId);
    }
   @Override
   public void reduceProductStock(int quantity, long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product ID " + productId);
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
    @Override
    public void addCartProduct(ProductDTO productDTO, ShoppingCart cart, int quantity){
        Product product = productMapper.toEntity(productDTO);
        CartProduct newCartProduct = new CartProduct(product, cart, quantity);
        cartProductRepository.save(newCartProduct);
    }

    @Override
    public CartItem createCartItem(Long userId, Long productId, int quantity) {
        ShoppingCart cart = userRepository.findShoppingCartByUserId(userId);
        Product product = productRepository.findProductByProductIdAndStockGreaterThanEqual(quantity, productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found or insufficient stock"));
        ProductDTO productDTO = productMapper.toDTO(product);
        return new CartItem(cart, productDTO);
    }
    @Override
    public boolean isProductInUserCart(Long productId, Long userId){
        ProductDTO product = productService.findAvailableProductById(productId);
        ShoppingCart cart = userRepository.findByUserId(userId).orElseThrow(() ->
                        new UserNotFoundException("User not found"))
                       .getShoppingCart();
       return cartProductRepository.findAllByShoppingCart(cart)
                .stream()
                .anyMatch(cartProduct -> cartProduct.getProduct().equals(product));
    }
    public void clearCart(){
    }
    public void removeProductFromCart(){
    }
}

