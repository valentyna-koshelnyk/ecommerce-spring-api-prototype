package com.startsteps.ecommerceapi.payload.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
public class SearchCriteria {
    private List<Filter> filters;

    @Override
    public String toString() {
        return filters.stream()
                .map(filter -> filter.getField() + " " + filter.getOperator() + " " + filter.getValue())
                .collect(Collectors.joining(", "));
    }

    @Getter
    @Builder(toBuilder = true)
    public static class Filter {
        public enum QueryOperator {
            EQUALS, NOT_EQUALS, LIKE, LESS_THAN, GREATER_THAN
        }

        private String field;
        private QueryOperator operator;
        private String value;

    }

}
