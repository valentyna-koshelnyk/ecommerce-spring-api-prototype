package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.payload.request.ProductAddRequest;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import com.startsteps.ecommerceapi.service.ProductServiceImpl;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart")
public class CartController {

    private final CartServiceImpl cartService;
    private final ProductServiceImpl productService;

    @Autowired
    public CartController(CartServiceImpl cartService, ProductServiceImpl productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
    @PostMapping("/{userId}/addProduct/")
    public ResponseEntity<MessageResponse> addProductToShoppingCart(@PathVariable Long userId,
                                                                    @RequestBody ProductAddRequest request) {
        String name = productService.findProductByProductId(request.getProductId()).getProductName();
        cartService.addProductToCart(request, userId);
        MessageResponse response = new MessageResponse("Product " + name + " added to the shopping cart");
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
    @GetMapping("/{userId}/checkCart")

    public ResponseEntity<?> viewProductList(
            @PathVariable Long userId) {
        Long shoppingCartId = cartService.findShoppingCartByUser(userId).getCartId();
        return ResponseEntity.ok(new MessageResponse(cartService.getProductsInCart(shoppingCartId).toString()));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<MessageResponse> removeProductFromCart(@PathVariable Long userId, @RequestParam Long cartId, @PathVariable("productId") Long productId){
        ProductDTO product = productService.findProductByProductId(productId);
        String productName = product.getProductName();
        cartService.removeProductFromCart(cartId, productId);
        MessageResponse response = new MessageResponse("Product " + productName +  " removed from your shopping cart");
        return ResponseEntity.ok(response);
    }


}


