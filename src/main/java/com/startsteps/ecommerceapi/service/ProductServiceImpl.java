package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.exceptions.ProductAlreadyExistsException;
import com.startsteps.ecommerceapi.exceptions.ProductNotFoundException;
import com.startsteps.ecommerceapi.exceptions.StockCannotBeNegativeException;
import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.payload.request.SearchCriteria;
import com.startsteps.ecommerceapi.payload.response.ProductResponse;
import com.startsteps.ecommerceapi.persistence.CartProductRepository;
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
    private final ProductMapper productMapper;
    private final CartProductRepository cartProductRepository;
    private final int MIN_STOCK = 1;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, EntitySpecification<Product> entitySpecification, ProductMapper productMapper, CartProductRepository cartProductRepository) {
        this.productRepository = productRepository;
        this.entitySpecification = entitySpecification;
        this.productMapper = productMapper;
        this.cartProductRepository = cartProductRepository;
    }
    @Override
    public ProductResponse findAllProducts(PageRequest pageable) {
        var productsPage= this.productRepository.findAll(pageable);
        return buildResponse(productsPage);
    }

    @Override
    public ProductDTO findAvailableProductById(Long productId) {
        Product product = productRepository.findProductByProductIdAndStockGreaterThanEqual(productId, MIN_STOCK)
                .orElseThrow(() -> new ProductNotFoundException("Product with the id " + productId +
                        " has not been found"));

        return productMapper.toDto(product);
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
        log.info("Attempting to add product: {}", productDTO.getProductName());
        if (productRepository.existsByProductName(productDTO.getProductName())){
            throw new ProductAlreadyExistsException("Product already exists with name: " +
                    productDTO.getProductName() + ". Use increase stock.");
        }
        Product product = productMapper.toEntity(productDTO);
        try {
            Product saveProduct = productRepository.save(product);

            log.info("Product added successfully: {}", saveProduct.getProductId());
            return productMapper.toDto(saveProduct);
        } catch (RuntimeException e) {
            log.error("Error adding product: {}", e.getMessage(), e);
            log.error("Error adding product with DTO: {}", productDTO, e);

            throw e;
        }
    }


    @Override
    public  void increaseProductStock(Long productId, Long quantity){
        Product product = productRepository.findProductByProductId(productId).orElseThrow(() -> new ProductNotFoundException("Product not found. Use add product"));
        product.setStock(product.getStock() + quantity);
    }

    @Override
    public void decreaseProductStock(Long productId, Long quantity){
        Product product = productRepository.findProductByProductId(productId).orElseThrow(() -> new ProductNotFoundException("Product not found. Use add product"));
        if(product.getStock() - quantity > 0){
                throw new StockCannotBeNegativeException("Stock is lower than quantity. Impossible to decrease");
        } else {
            product.setStock(product.getStock() - quantity);
        }
    }
    @Override
    public void deleteProductByCriteria(SearchCriteria searchCriteria) {
        Specification<Product> spec = entitySpecification.specificationBuilder(searchCriteria);
        List<Product> productsToDelete = productRepository.findAll(spec);
        List<CartProduct> cartProductList =  cartProductRepository.findCartProductByProductIn(productsToDelete);
        cartProductRepository.deleteAll(cartProductList);
        productRepository.deleteAll(productsToDelete);
    } //TODO: to notify users that product is not available anymore

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
    public ProductDTO findProductByProductId(Long productId){
        Product product = productRepository.findProductByProductId(productId).orElseThrow(()
        -> new ProductNotFoundException("Product with id "
                + productId + " not found"));
        return productMapper.toDto(product);
    }

    @Override
    public List<Product> findProductByName(String productName) {
        List<Product> productList = productRepository.findAllByProductNameContainingIgnoreCaseAndStockGreaterThan(MIN_STOCK, productName);
        if(productList.isEmpty()){
            throw new ProductNotFoundException("Product with such name " + productName + " not found");
        } else {
            return productList;
        }
    }

    }



