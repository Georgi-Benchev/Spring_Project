package com.example.spring_demo.repositorys;

import com.example.spring_demo.models.Beer;

import java.util.List;

public interface BeerRepository {
    List<Beer> getAll();

    Beer getBeerById(int id);

    void create(Beer beer);

    void update(Beer beer);

    Beer getByName(String name);

    void delete(int id);
}
