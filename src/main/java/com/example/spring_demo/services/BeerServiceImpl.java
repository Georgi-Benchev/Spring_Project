package com.example.spring_demo.services;

import com.example.spring_demo.exceptions.DuplicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.exceptions.UnauthorizedAccessException;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.FilterOptions;
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
    public List<Beer> getAll(FilterOptions filterOptions) {
        return beerRepository.getAll(filterOptions);
    }

    @Override
    public Beer getBeerById(int id) {
        return beerRepository.getBeerById(id);
    }


    @Override
    public Beer create(Beer beer, User user) {
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
            throw new DuplicateEntityException("Beer", "id", String.valueOf(beer.getId()));
        }
        if (nameAlreadyExists) {
            throw new DuplicateEntityException("Beer", "name", beer.getName());
        }
        beer.setCreatedBy(user);
        beerRepository.create(beer, user);
        return beer;
    }


    @Override
    public Beer update(Beer beer, User user) {
        validateUser(user, beer.getId());
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
            throw new DuplicateEntityException("Beer", "name", beer.getName());
        }

        beerRepository.update(beer, beer.getId());
        return beer;
    }

    @Override
    public Beer delete(int id, User user) {
        validateUser(user, id);
        Beer beer = getBeerById(id);

        beerRepository.delete(id);
        return beer;
    }

    @Override
    public Beer getBeerByName(String name) {
        return beerRepository.getByName(name);
    }

    private void validateUser(User user, int id) {
        Beer beer = beerRepository.getBeerById(id);
        if (!user.isAdmin() && !beer.getCreatedBy().equals(user)) {
            throw new UnauthorizedAccessException(
                    "You can modify/delete the beer only if you are Admin or you added the beer");
        }
    }
}
