package com.example.spring_demo.services;

import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.FilterOptions;
import com.example.spring_demo.models.User;

import java.util.List;

public interface BeerService {

    List<Beer> getAll(FilterOptions filterOptions);

    Beer getBeerById(int id);

    Beer create(Beer beer, User user);

    Beer update(Beer beer, User user);

    Beer delete(int id, User user);

    Beer getBeerByName(String name);
}
