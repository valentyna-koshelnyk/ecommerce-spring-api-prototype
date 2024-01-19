package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.ProductAlreadyExistsException;
import com.startsteps.ecommerceapi.exceptions.ProductNotFoundException;
import com.startsteps.ecommerceapi.exceptions.UserNotFoundException;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.persistence.ProductRepository;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    ProductRepository productRepository;

    @Override
    public Product addProduct(ProductDTO product) {
        if (productRepository.existsByProductName(product.getProductName())){
            throw new ProductAlreadyExistsException("The product with the name \"" + product.getProductName() +
                    "\" already exists");
        }
        Product newProduct = new Product(product.getProductName(), product.getPrice(), product.getDescription(),
                product.getStock(), product.getProductCategory());
        newProduct.setAddedAtDate(LocalDateTime.now());
        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> findProductInPriceRange(double priceMin, double priceMax) {
        if(priceMin >= priceMax){
            throw new IllegalArgumentException("Invalid price range: " +
                    "The minimum price must be less than the maximum price.");
        }
        if(priceMax < 0 || priceMin < 0){
            throw new IllegalArgumentException("Price cannot be negative. But you can check our charity project ;)");
        }
        return productRepository.findProductByPriceBetween(priceMin, priceMax);
    }


    @Override
    public void deleteProductById(long id) {
        productRepository.deleteByProductId(id);
    }

    @Override
    public void deleteByProductByName(String name) {
        productRepository.deleteByProductNameIgnoreCase(name);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteAllByAddedAtDate(LocalDateTime date) {
         productRepository.deleteAllByAddedAtDate(date);
    }

    @Override
    public Product updateProductByPrice(long id, double price) {
        if(!productRepository.existsByProductId(id)){
            throw new ProductNotFoundException("Product with the id: " + id + " not found");
        }
        Product product = productRepository.findByProductId(id).orElseThrow(() ->
                new ProductNotFoundException("Product with the id " + id + " not found"));
        product.setPrice(price);
        return product;
    }
}
