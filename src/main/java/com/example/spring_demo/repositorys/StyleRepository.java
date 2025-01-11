package com.example.spring_demo.repositorys;

import com.example.spring_demo.models.Style;

import java.util.List;

public interface StyleRepository {
    List<Style> getAll();

    Style getById(int id);
}
