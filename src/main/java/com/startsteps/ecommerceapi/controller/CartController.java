package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.payload.request.ProductAddRequest;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import com.startsteps.ecommerceapi.service.ProductServiceImpl;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/addProduct/")
    public ResponseEntity<MessageResponse> addProductToShoppingCart(@RequestBody ProductAddRequest request) {
        cartService.addProductToCart(request);
        MessageResponse response = new MessageResponse("Product added to the shopping cart");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkCart")
    public ResponseEntity<?> viewProductList(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "productName") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction,
            @RequestParam Long cartId
    ) {
        var pageRequestData = PageRequest.of(pageNumber - 1, size, Sort.Direction.valueOf(direction), sort);
        return ResponseEntity.ok(cartService.getProductsInCart(cartId, pageRequestData));

    }
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<MessageResponse> removeProductFromCart(@RequestParam Long cartId, @PathVariable("productId") Long productId){
        cartService.removeProductFromCart(cartId, productId);
        ProductDTO product = productService.findProductByProductId(productId);
        String productName = product.getProductName();
        MessageResponse response = new MessageResponse("Product " + productName +  " removed from your shopping cart");
        return ResponseEntity.ok(response);

    }

}


