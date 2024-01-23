package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.exceptions.ProductNotFoundException;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.service.ProductServiceImpl;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** for arrays use pagination
 * for identifiers use strings
 * use strings for dates
 */

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<Product> pageProducts;
        if (name == null || name.isBlank()) {
            pageProducts = (Page<Product>) productService.findAllSortedByPrice(paging);
        } else {
            pageProducts = productService.findProductByProductNameContainingIgnoreCase(name.trim(), paging);
        }
        List<Product> productList = pageProducts.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("products", productList);
        response.put("currentPage", pageProducts.getNumber());
        response.put("totalItems", pageProducts.getTotalElements());
        response.put("totalPages", pageProducts.getTotalPages()); // This should be getTotalPages(), not getTotalElements()
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/{offset}/{pageSize}/{field}")
    public ResponseEntity<?> getAllProductsSortedByField(@PathVariable int offset,
                                                        @PathVariable int pageSize,
                                                        @PathVariable String field){
        Page<Product> products = productService.fin(offset, pageSize, field);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping("/admin/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productService.addProduct(productDTO);
        return ResponseEntity.ok(new MessageResponse("Product added successfully! ProductID: " + product.getProductId()));
    }

    @PutMapping("/admin/update/{id}/price")
    public ResponseEntity<?> updateProductPriceById(@PathVariable long id, @RequestParam double price) {
        Product oldPriceProduct = productService.findByProductId(id);
        Product updatedProduct = productService.updateProductByPrice(id, price);
        return ResponseEntity.ok(new MessageResponse("Product " + updatedProduct.getProductName() +
                " updated successfully. " + "\n Old Price: " + oldPriceProduct.getPrice() +
                " \n New price is: " + updatedProduct.getPrice()));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable long id) {
        String productName =   productService.findByProductId(id).getProductName();
        productService.deleteProductById(id);
        return ResponseEntity.ok(new MessageResponse("Product " + productName + " was deleted"));

    }
}





