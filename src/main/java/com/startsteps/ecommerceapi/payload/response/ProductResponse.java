package com.startsteps.ecommerceapi.payload.response;

import com.startsteps.ecommerceapi.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProductResponse {

    private final List<Product> products;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final boolean isLastPage;

}

