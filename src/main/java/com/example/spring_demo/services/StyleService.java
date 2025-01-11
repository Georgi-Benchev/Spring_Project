package com.example.spring_demo.services;

import com.example.spring_demo.models.Style;

import java.util.List;

public interface StyleService {
    public List<Style> getAll();

    public Style getById(int id);
}
