package com.example.spring_demo.repositorys;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Beer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BeerRopositoryImpl implements BeerRepository {

    private final List<Beer> beers;


    public BeerRopositoryImpl() {
        this.beers = new ArrayList<>();
    }

    @Override
    public List<Beer> getAll() {
        return beers;
    }

    @Override
    public Beer getBeerById(int id) {
        return beers.stream()
                .filter(beer -> beer.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Beer", "id", String.valueOf(id)));
    }

    @Override
    public Beer getByName(String name) {
        return beers.stream()
                .filter(beer -> name.equals(beer.getName()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Beer", "name", name));
    }


    @Override
    public void create(Beer beer) {
        beers.add(beer);
    }

    @Override
    public void update(Beer beer) {
        Beer beerToUpdate = getBeerById(beer.getId());
        beerToUpdate.setName(beer.getName());
        beerToUpdate.setAbv(beer.getAbv());
    }


    @Override
    public void delete(int id) {
        Beer beerToDelete = getBeerById(id);
        beers.remove(beerToDelete);
    }
}
