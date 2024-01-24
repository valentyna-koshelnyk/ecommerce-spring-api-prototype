package com.startsteps.ecommerceapi.utils;

import com.startsteps.ecommerceapi.payload.request.SearchCriteria;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Component
public class EntitySpecification<T> {

    public Specification<T> specificationBuilder(SearchCriteria searchCriteria) {
        if(Objects.nonNull(searchCriteria) && CollectionHelper.isNotEmpty(searchCriteria.getFilters())) {
            List<SearchCriteria.Filter> filters = searchCriteria.getFilters();
            List<Specification<T>> specifications = filters.stream()
                    .map(this::createSpecification)
                    .collect(Collectors.toList());

            return Specification.allOf(specifications);
        }
        return null;
    }

    private Specification<T> createSpecification(SearchCriteria.Filter filter) {
        switch (filter.getOperator()) {

            case EQUALS:
                return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(filter.getField()), filter.getValue());

            case NOT_EQUALS:
                return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(filter.getField()), filter.getValue());

            case GREATER_THAN:
                return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), filter.getValue());

            case LESS_THAN:
                return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), filter.getValue());

            case LIKE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(filter.getField()), "%" + filter.getValue() + "%");

            default:
                return null;

        }
    }

}