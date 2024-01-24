package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.ProductAlreadyExistsException;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.payload.request.SearchCriteria;
import com.startsteps.ecommerceapi.payload.response.ProductResponse;
import com.startsteps.ecommerceapi.persistence.ProductRepository;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
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
    public ProductServiceImpl(ProductRepository productRepository, EntitySpecification<Product> entitySpecification) {
        this.productRepository = productRepository;
        this.entitySpecification = entitySpecification;
    }
    @Override
    public ProductResponse findAllProducts(PageRequest pageable) {
        var productsPage= this.productRepository.findAll(pageable);
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
    public void deleteProductByCriteria(SearchCriteria searchCriteria) {
        Specification<Product> spec = entitySpecification.specificationBuilder(searchCriteria);
        List<Product> productsToDelete = productRepository.findAll(spec);
        productRepository.deleteAll(productsToDelete);
    }


}
