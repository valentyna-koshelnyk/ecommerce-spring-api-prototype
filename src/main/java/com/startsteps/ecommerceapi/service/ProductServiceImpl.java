package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.ProductAlreadyExistsException;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.persistence.ProductRepository;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    ProductRepository productRepository;

    @Override
    public Product addProduct(ProductDTO product) {
       if (productRepository.existsByProductName(product.getProductName())){
           throw new ProductAlreadyExistsException("The product with the name \"" + product.getProductName() +
                   "\" already exists");
       }
        Product newProduct = new Product(product.getProductName(), product.getPrice(), product.getDescription(),
                 product.getStock(), product.getProductCategory());
       return productRepository.save(newProduct);
    }

    @Override
    public List<Product> findProductInPriceRange(double priceMin, double priceMax) {
        if(priceMin >= priceMax){
            throw new IllegalArgumentException("Invalid price range: " +
                    "The minimum price must be less than the maximum price.");
        }
        if(priceMax < 0 || priceMin < 0){
            throw new IllegalArgumentException("Price cannot be negative. But you can check our charity project :)");
        }
        return productRepository.findProductByPriceBetween(priceMin, priceMax);
    }

    @Override
    public List<Product> findAllSortedByPrice(Pageable pageable) {
        Pageable sortedByPriceDesc = PageRequest.of(0,3, Sort.by("price").descending());
    return (List<Product>) sortedByPriceDesc;} // todo: update the method

    @Override
    public void deleteProductById(long id) {
    }
    public Product findProductByProductNameContainingIgnoreCase()

    @Override
    public void deleteByProductByName(String name) {

    }

    @Override
    public List<Product> findAllSortedByDate() {
        return null;
    }

    @Override
    public void deleteAllByAddedAtDate(LocalDateTime date) {

    }

    @Override
    public Optional<Product> updateProductByPrice(long id, double price) {
        return Optional.empty();
    }
}
