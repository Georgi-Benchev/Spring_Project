package com.example.spring_demo.repositorys;

import com.example.spring_demo.models.Beer;

import java.util.List;

public interface BeerRepository {
    List<Beer> get(String name,
                   Double minAbv,
                   Double maxAbv,
                   Integer styleId,
                   String sortBy,
                   String orderBy);

    Beer getBeerById(int id);

    Beer getByName(String name);

    void create(Beer beer);

    void update(Beer beer);

    void delete(int id);
}
