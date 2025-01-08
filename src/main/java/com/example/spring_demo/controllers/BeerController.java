package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.DublicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.services.BeerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/beers")

public class BeerController {

    private final BeerService beerService;


    @Autowired
    public BeerController(BeerService beerService) {
       /* ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        this.beerService = context.getBean(BeerService.class);*/
        this.beerService = beerService;
    }

    @GetMapping
    public List<Beer> getBeers() {
        return beerService.getAll();
    }

    @GetMapping("/{id}")
    public Beer getBeer(@PathVariable int id) {
        try {
            return beerService.getBeerById(id);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping
    public Beer createBeer(@Valid @RequestBody Beer beer) {
        try {
            beerService.create(beer);
        } catch (DublicateEntityException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }

        return beer;
    }

    @PutMapping("/{id}")
    public Beer updateBeer(@Valid @RequestBody Beer beer, @PathVariable int id) {

        /*
        if (id != beer.getId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "URL id doesn't match entity id");
        }*/

        try {
            beerService.update(beer);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (DublicateEntityException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }

        return beerService.getBeerById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBeer(@PathVariable int id) {
        try {
            beerService.delete(id);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }
}
