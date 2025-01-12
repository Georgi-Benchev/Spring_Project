package com.example.spring_demo.services;

import com.example.spring_demo.models.Beer;

import java.util.List;

public interface BeerService {
    List<Beer> getAll(String name, Double minAbv, Double maxAbv, Integer styleId, String sortBy, String orderBy);

    Beer getBeerById(int id);

    Beer getByName(String name);

    void create(Beer beer);

    void update(Beer beer);

    void delete(int id);

}
