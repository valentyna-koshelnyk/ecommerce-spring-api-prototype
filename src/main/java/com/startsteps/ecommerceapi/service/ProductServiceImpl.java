package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.ProductAlreadyExistsException;
import com.startsteps.ecommerceapi.exceptions.ProductNotFoundException;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.payload.request.SearchCriteria;
import com.startsteps.ecommerceapi.payload.response.ProductResponse;
import com.startsteps.ecommerceapi.persistence.ProductRepository;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import com.startsteps.ecommerceapi.service.dto.ProductMapper;
import com.startsteps.ecommerceapi.utils.EntitySpecification;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final EntitySpecification<Product> entitySpecification;
    @Autowired
    private  ProductMapper productMapper;
    private final int MIN_STOCK = 1;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, EntitySpecification<Product> entitySpecification) {
        this.productRepository = productRepository;
        this.entitySpecification = entitySpecification;
    }
    @Override
    public ProductResponse findAllProducts(PageRequest pageable) {
        var productsPage= this.productRepository.findAll(pageable);
        return buildResponse(productsPage);
    }

    @Override
    public ProductDTO findAvailableProductById(Long productId) {
        Product product = productRepository.findProductByProductIdAndStockGreaterThanEqual(MIN_STOCK, productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with the id " + productId +
                        " has not been found"));
        ProductDTO productDTO = productMapper.toDto(product);

        return productDTO;
    }

    @Override
    public ProductResponse findAllAvailableProducts(PageRequest pageable) {
        var productsPage = this.productRepository.findAllByStockGreaterThanEqual(MIN_STOCK, pageable);
        return buildResponse(productsPage);
    }

    private ProductResponse buildResponse(Page productsPage){
        return ProductResponse.builder()
                .pageNumber(productsPage.getNumber() + 1)
                .pageSize(productsPage.getSize())
                .totalElements(productsPage.getTotalElements())
                .totalPages(productsPage.getTotalPages())
                .products(productsPage.toList())
                .isLastPage(productsPage.isLast())
                .build();
    }


    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        if (productRepository.existsByProductName(productDTO.getProductName())){
            throw new ProductAlreadyExistsException("The product with the name \"" + productDTO.getProductName() +
                    "\" already exists");
        }
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productDTO;
    }

    @Override
    public void deleteProductByCriteria(SearchCriteria searchCriteria) {
        Specification<Product> spec = entitySpecification.specificationBuilder(searchCriteria);
        List<Product> productsToDelete = productRepository.findAll(spec);
        productRepository.deleteAll(productsToDelete);
    }

    @Override
    public void updateProductByCriteria(SearchCriteria searchCriteria, ProductDTO productDTO) {
        Specification<Product> spec = entitySpecification.specificationBuilder(searchCriteria);
        List<Product> productsToUpdate = productRepository.findAll(spec);
        Product product = productMapper.toEntity(productDTO);
        for (Product product1 : productsToUpdate) {
            if (product.getProductName() != null) {
                product1.setProductName(product.getProductName());
            }
            if (product.getDescription() != null) {
                product1.setDescription(product.getDescription());
            }
            if (product.getPrice() > 0) {
                product1.setPrice(product.getPrice());
            }
            if (product.getStock() > 0) {
                product1.setStock(product.getStock());
            }
            if (product.getCategory() != null) {
                product1.setCategory(product.getCategory());
            }
        }
        productRepository.saveAll(productsToUpdate);
    }

    @Override
    public List<Product> findProductByName(String productName) {
        List<Product> productList = productRepository.findAllByProductNameContainingIgnoreCaseAndStockGreaterThan(MIN_STOCK, productName);
        if(productRepository.findProductByProductNameContainingIgnoreCaseAndStockGreaterThan(MIN_STOCK, productName).isEmpty()){
            throw new ProductNotFoundException("Product with such name " + productName + " not found");
        } else {
            productList  =  productRepository.findAllByProductNameContainingIgnoreCaseAndStockGreaterThan(MIN_STOCK, productName);
            return productList;

        }
    }
}
