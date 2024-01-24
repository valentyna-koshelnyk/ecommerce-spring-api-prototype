package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.exceptions.ProductNotFoundException;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.payload.request.SearchCriteria;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.service.ProductServiceImpl;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<?> fetchAllProducts(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "productName") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction
    ) {
        var pageRequestData = PageRequest.of(pageNumber - 1, size, Sort.Direction.valueOf(direction), sort);
        return new ResponseEntity<>(productService.findAllProducts(pageRequestData), HttpStatus.PARTIAL_CONTENT);
    }
    @PostMapping("/admin/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productService.addProduct(productDTO);
        return ResponseEntity.ok(new MessageResponse("Product added successfully! ProductID: " + product.getProductId()));
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<String> deleteProductByCriteria(
            @RequestParam String field,
            @RequestParam String operator,
            @RequestParam String value) {

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .filters(List.of(SearchCriteria.Filter.builder()
                        .field(field)
                        .operator(SearchCriteria.Filter.QueryOperator.valueOf(operator))
                        .value(value)
                        .build()))
                .build();

        productService.deleteProductByCriteria(searchCriteria);
        String criteriaString = searchCriteria.toString();
        return ResponseEntity.ok("Product matching criteria " + criteriaString + " has been deleted.");
    }


}






