package com.example.spring_demo.models;

import java.util.Objects;

public class Style {

    private int id;
    private String name;

    public Style() {
    }

    public Style(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Style style = (Style) o;
        return id == style.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
