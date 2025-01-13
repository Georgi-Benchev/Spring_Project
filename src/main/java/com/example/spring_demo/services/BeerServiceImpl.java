package com.example.spring_demo.services;

import com.example.spring_demo.exceptions.DublicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.exceptions.UnauthorizedAccessException;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.User;
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
    public List<Beer> getAll(String name, Double minAbv, Double maxAbv, Integer styleId, String sortBy, String orderBy) {
        return beerRepository.get(name, minAbv, maxAbv, styleId, sortBy, orderBy);

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
    public void create(Beer beer, User user) {
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

        beerRepository.create(beer, user);
    }


    @Override
    public void update(Beer beer, User user) {
        validateUser(user, beerRepository.getByName(beer.getName()));
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
    public void delete(int id, User user) {
        Beer beerToDelete = getBeerById(id);
        validateUser(user, beerToDelete);
        beerRepository.delete(id);
    }

    private void validateUser(User user, Beer beer) {
        if (!user.isAdmin() || beer.getCreatedBy().getId() != user.getId()) {
            throw new UnauthorizedAccessException(
                    "You can update the beer only if you are Admin or you added the beer");
        }

    }
}
