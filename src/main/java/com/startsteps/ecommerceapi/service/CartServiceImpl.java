package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.CartNotFoundException;
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
import com.startsteps.ecommerceapi.service.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class CartServiceImpl implements CartService{

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;
    private final CartProductMapper cartProductMapper;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private static final Long MIN_STOCK = 1L;
    private static final Double REMOVE_COST = 0D;

    private ProductDTO product;
    private UserDTO user;
    private ProductServiceImpl productService;
    private final    ProductMapper productMapper;
    @Autowired
    public CartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, CartProductRepository cartProductRepository, CartProductMapper cartProductMapper, UserRepository userRepository, ShoppingCartMapper shoppingCartMapper, ProductServiceImpl productService, ProductMapper productMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.cartProductMapper = cartProductMapper;
        this.userRepository = userRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.productService = productService;
        this.productMapper = productMapper;
    }
   @Override
   public void addProductToCart(ProductAddRequest request) {
       Product product = productRepository.findProductByProductIdAndStockGreaterThanEqual(
                       request.getProductId(), MIN_STOCK)
               .orElseThrow(() -> new ProductNotFoundException(
                       "Product with ID " + request.getProductId() + " not found."
               ));

       ShoppingCart cart = shoppingCartRepository.findById(request.getCartId())
               .orElseThrow(() -> new CartNotFoundException("Shopping cart doesn't exist"));

       if (isProductInUserCart(product, cart)) {
           CartProduct cartProduct = cartProductRepository.findCartProductByProductAndShoppingCart(product, cart)
                   .orElseThrow(() -> new CartNotFoundException("CartProduct could not be fetched from the database"));

           cartProduct.setQuantity(cartProduct.getQuantity() + request.getQuantity());
           cartProduct.setPriceProduct(calculateProductCost(product, cartProduct.getQuantity()));
           cartProductRepository.save(cartProduct); // Ensure you save the updated cartProduct
       } else {
           addNewProductToCart(product, cart, request.getQuantity()); // Pass the Product entity, not DTO
       }
       reduceProductStock(request.getQuantity(), request.getProductId());
       getTotalCost(request.getCartId());
   }
   @Override
   public void getTotalCost(Long shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByCartId(shoppingCartId)
                .orElseThrow(()-> new CartNotFoundException("Cart is not found"));
       List<CartProduct> cartProducts = cartProductRepository.findCartProductByShoppingCart(shoppingCart);
       double totalCost = 0.0;
       for(CartProduct cp : cartProducts){
           totalCost += cp.getPriceProduct();
       }
       shoppingCart.setPriceTotal(totalCost);
       shoppingCartRepository.save(shoppingCart);
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
    public void increaseStock(Long productId, Long quantity){
        Product product = productRepository.findProductByProductId(productId).orElseThrow(()->
                new ProductNotFoundException("Product cannot be returned"));
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }
    @Override
    public void addNewProductToCart(Product product, ShoppingCart cart, Long quantity){
        CartProduct newCartProduct = new CartProduct(product, cart, quantity);
        newCartProduct.setPriceProduct(calculateProductCost(product, quantity));
        cart.setPriceTotal(newCartProduct.getPriceProduct());
        cartProductRepository.save(newCartProduct);
    }

    @Override
    public ShoppingCart findShoppingCartByCartId(Long cartId){
        return shoppingCartRepository.findShoppingCartByCartId(cartId).orElseThrow(()
                -> new CartNotFoundException("Cart is empty"));
    }

    @Override
    public ShoppingCart findShoppingCartByUser(Long userId){
       return shoppingCartRepository.findShoppingCartByUserId(userId);
    }
    @Override
    public double calculateProductCost(Product product, Long quantity){
        return product.getPrice() * quantity;
    }

    @Override
    public boolean isProductInUserCart(Product product, ShoppingCart shoppingCart) {
       return cartProductRepository.findCartProductByProductAndShoppingCart(product, shoppingCart)
                .isPresent();
    }
    @Transactional
    @Override
    public Page<CartProductDTO> getProductsInCart(Long cartId, Pageable pageable) {
    ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
            .orElseThrow(() -> new CartNotFoundException("Shopping cart is empty"));
    Page<CartProduct> cartProductsPage = cartProductRepository.findAllByShoppingCart(shoppingCart, pageable);
    return cartProductMapper.toDtoPage(cartProductsPage);
}
    @Override
    public void removeProductFromCart(Long cartId, Long productId){ //removes entire product from the cart
        Product product = productRepository.findProductByProductId(productId)
                .orElseThrow(()-> new ProductNotFoundException("There's no product to return"));
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Shopping cart doesn't exist"));
        CartProduct cartProduct = cartProductRepository.findCartProductByProductAndShoppingCart(product, shoppingCart)
                .orElseThrow(() -> new ProductNotFoundException("The product is not present in the car"));
        Long quantity = cartProduct.getQuantity();
        increaseStock(productId,quantity);

        cartProductRepository.deleteByShoppingCartAndProduct(shoppingCart, product);

    }

    @Override
    @Transactional
    public void emptyCart(Long cartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByCartId(cartId)
                .orElseThrow(()-> new CartNotFoundException("Cart doesn't exist"));

        List<CartProduct> cartProduct = cartProductRepository.findCartProductByShoppingCart(shoppingCart);
       for(CartProduct cp: cartProduct){
           increaseStock(cp.getProduct().getProductId(), cp.getQuantity());
           cartProductRepository.delete(cp);
       }
       shoppingCart.setPriceTotal(REMOVE_COST);
       shoppingCartRepository.save(shoppingCart);
    }

}

