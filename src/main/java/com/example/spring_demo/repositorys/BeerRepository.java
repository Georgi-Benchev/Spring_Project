package com.example.spring_demo.repositorys;

import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.FilterOptions;
import com.example.spring_demo.models.User;

import java.util.List;

public interface BeerRepository{

    List<Beer> getAll(FilterOptions filterOptions);

    Beer getBeerById(int id);

    Beer getByName(String name);

    void create(Beer beer, User user);

    void update(Beer beer, int id);

    void delete(int id);
}
