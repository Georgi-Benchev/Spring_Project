package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.DublicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.helpers.BeerMapper;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.BeerDTO;
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
    private final BeerMapper beerMapper;


    @Autowired
    public BeerController(BeerService beerService, BeerMapper beerMapper) {
       /* ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        this.beerService = context.getBean(BeerService.class);*/
        this.beerService = beerService;
        this.beerMapper = beerMapper;
    }

    @GetMapping
    public List<Beer> getBeers(@RequestParam(required = false) String name,
                               @RequestParam(required = false) Double minAbv,
                               @RequestParam(required = false) Double maxAbv,
                               @RequestParam(required = false) Integer styleId,
                               @RequestParam(required = false) String sortBy,
                               @RequestParam(required = false) String orderBy) {
        return beerService.getAll(name, minAbv, maxAbv, styleId, sortBy, orderBy);
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
    public Beer createBeer(@Valid @RequestBody BeerDTO beerDTO) {
        try {
            Beer beer = beerMapper.fromDto(beerDTO);
            beerService.create(beer);
            return beer;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DublicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Beer updateBeer(@Valid @RequestBody BeerDTO beerDTO, @PathVariable int id) {

        /*
        if (id != beer.getId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "URL id doesn't match entity id");
        }*/

        try {
            Beer beer = beerMapper.fromDto(id, beerDTO);
            beerService.update(beer);
            return beer;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DublicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
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
