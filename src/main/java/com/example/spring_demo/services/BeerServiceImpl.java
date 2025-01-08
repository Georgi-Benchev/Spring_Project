package com.example.spring_demo.services;

import com.example.spring_demo.exceptions.DublicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.repositorys.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }


    @Override
    public List<Beer> getAll() {
        return beerRepository.getAll();
    }

    @Override
    public Beer getBeerById(int id) {
        return beerRepository.getBeerById(id);
    }

    @Override
    public Beer getByName(String name) {
        /*return beerRepository.getByName(name);*/
        return null;
    }

    @Override
    public void create(Beer beer) {
        boolean idAlreadyExists = true;
        boolean nameAlreadyExists = true;

        try {
            beerRepository.getBeerById(beer.getId());
        } catch (EntityNotFoundException exception) {
            idAlreadyExists = false;
        }
        try {
            beerRepository.getByName(beer.getName());
        } catch (EntityNotFoundException exception) {
            nameAlreadyExists = false;
        }

        if (idAlreadyExists) {
            throw new DublicateEntityException("Beer", "id", String.valueOf(beer.getId()));
        }
        if (nameAlreadyExists) {
            throw new DublicateEntityException("Beer", "name", beer.getName());
        }

        beerRepository.create(beer);
    }


    @Override
    public void update(Beer beer) {
        boolean beerAlreadyExists = true;

        try {
            Beer local = beerRepository.getByName(beer.getName());
            if (local.getId() == beer.getId()) {
                beerAlreadyExists = false;
            }
        } catch (EntityNotFoundException exception) {
            beerAlreadyExists = false;
        }
        if (beerAlreadyExists) {
            throw new DublicateEntityException("Beer", "name", beer.getName());
        }

        beerRepository.update(beer);
    }


    @Override
    public void delete(int id) {
        beerRepository.delete(id);
    }
}
