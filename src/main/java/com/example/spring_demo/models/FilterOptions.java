package com.example.spring_demo.models;

import java.util.Optional;

public class FilterOptions {

    private Optional<String> name;
    private Optional<Double> minAbv;
    private Optional<Double> maxAbv;
    private Optional<Integer> styleId;
    private Optional<String> sortBy;
    private Optional<String> orderBy;

    public FilterOptions(String name,
                            Double minAbv,
                            Double maxAbv,
                            Integer styleId,
                            String sortBy,
                            String orderBy) {
        this.name = Optional.ofNullable(name);
        this.minAbv = Optional.ofNullable(minAbv);
        this.maxAbv = Optional.ofNullable(maxAbv);
        this.styleId = Optional.ofNullable(styleId);
        this.sortBy = Optional.ofNullable(sortBy);
        this.orderBy = Optional.ofNullable(orderBy);
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<Double> getMinAbv() {
        return minAbv;
    }

    public Optional<Double> getMaxAbv() {
        return maxAbv;
    }

    public Optional<Integer> getStyleId() {
        return styleId;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getOrderBy() {
        return orderBy;
    }
}