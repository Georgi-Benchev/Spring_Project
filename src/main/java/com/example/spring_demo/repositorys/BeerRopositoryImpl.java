package com.example.spring_demo.repositorys;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BeerRopositoryImpl implements BeerRepository {

    private int nextId = 3;
    private final List<Beer> beers;


    @Autowired
    public BeerRopositoryImpl(StyleRepository styleRepository) {
        this.beers = new ArrayList<>();

        beers.add(new Beer(1, "Corona", 5.5, styleRepository.getById(1)));
        beers.add(new Beer(2, "Stella", 5.0, styleRepository.getById(2)));
        beers.add(new Beer(3, "Brixton", 4.5, styleRepository.getById(3)));
    }


    @Override
    public List<Beer> get(String name, Double minAbv, Double maxAbv, Integer styleId, String sortBy, String orderBy) {
        List<Beer> result = beers;
        result = filterByName(result, name);
        result = filterByAbv(result, minAbv, maxAbv);
        result = filterByStyle(result, styleId);
        result = sortBy(result, sortBy);
        result = order(result, orderBy);
        return result;
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
        beer.setId(++nextId);
        beers.add(beer);
    }

    @Override
    public void update(Beer beer) {
        Beer beerToUpdate = getBeerById(beer.getId());
        beerToUpdate.setName(beer.getName());
        beerToUpdate.setAbv(beer.getAbv());
        beerToUpdate.setStyle(beer.getStyle());
    }


    @Override
    public void delete(int id) {
        Beer beerToDelete = getBeerById(id);
        beers.remove(beerToDelete);
    }


    private static List<Beer> filterByName(List<Beer> beers, String name) {
        if (name != null && !name.isEmpty()) {
            beers = beers.stream()
                    .filter(beer -> containsIgnoreCase(beer.getName(), name))
                    .collect(Collectors.toList());
        }
        return beers;
    }

    private static List<Beer> filterByAbv(List<Beer> beers, Double minAbv, Double maxAbv) {
        if (minAbv != null) {
            beers = beers.stream()
                    .filter(beer -> beer.getAbv() >= minAbv)
                    .collect(Collectors.toList());
        }

        if (maxAbv != null) {
            beers = beers.stream()
                    .filter(beer -> beer.getAbv() <= maxAbv)
                    .collect(Collectors.toList());
        }

        return beers;
    }

    private static List<Beer> filterByStyle(List<Beer> beers, Integer styleId) {
        if (styleId != null) {
            beers = beers.stream()
                    .filter(beer -> beer.getStyle().getId() == styleId)
                    .collect(Collectors.toList());
        }
        return beers;
    }

    private static List<Beer> sortBy(List<Beer> beers, String sortBy) {
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "name":
                    beers.sort(Comparator.comparing(Beer::getName));
                    break;
                case "abv":
                    beers.sort(Comparator.comparing(Beer::getAbv));
                case "style":
                    beers.sort(Comparator.comparing(beer -> beer.getStyle().getName()));
                    break;
            }
        }
        return beers;
    }

    private static List<Beer> order(List<Beer> beers, String order) {
        if (order != null && !order.isEmpty()) {
            if (order.equals("desc")) {
                Collections.reverse(beers);
            }
        }
        return beers;
    }

    private static boolean containsIgnoreCase(String value, String sequence) {
        return value.toLowerCase().contains(sequence.toLowerCase());
    }
}
