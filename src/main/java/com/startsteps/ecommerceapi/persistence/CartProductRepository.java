package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {


    List<CartProduct> findAllByShoppingCart(ShoppingCart shoppingCart);
    CartProduct findCartProductByProduct(ProductDTO product);
    CartProduct findCartProductByProductAndShoppingCart(ProductDTO product, ShoppingCart shoppingCart);

}
