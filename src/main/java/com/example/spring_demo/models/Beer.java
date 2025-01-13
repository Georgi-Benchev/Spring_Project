package com.example.spring_demo.models;

import jakarta.validation.constraints.Positive;

import java.util.Objects;

public class Beer {
    @Positive(message = "id should be positive!")
    private int id;
    private String name;
    private double abv;
    private Style style;
    private User createdBy;

    public Beer() {
    }

    public Beer(int id, String name, double abv, Style style, User creator) {
        this.id = id;
        this.name = name;
        this.abv = abv;
        this.style = style;
        this.createdBy = creator;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return id == beer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
