package com.example.spring_demo.models.Dtos;

import jakarta.validation.constraints.Positive;

public class FilterOptionsDto {

    private String name;

    @Positive(message = "ABV is always positive!")
    private Double minAbv;

    @Positive(message = "ABV is always positive!")
    private Double maxAbv;

    private Integer styleId;

    private String sortBy;

    private String orderBy;

    public FilterOptionsDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinAbv() {
        return minAbv;
    }

    public void setMinAbv(Double minAbv) {
        this.minAbv = minAbv;
    }

    public Double getMaxAbv() {
        return maxAbv;
    }

    public void setMaxAbv(Double maxAbv) {
        this.maxAbv = maxAbv;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}