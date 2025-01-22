package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.DuplicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.exceptions.UnauthorizedAccessException;
import com.example.spring_demo.helpers.AuthenticationHelper;
import com.example.spring_demo.helpers.BeerMapper;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.BeerDto;
import com.example.spring_demo.models.FilterOptions;
import com.example.spring_demo.models.User;
import com.example.spring_demo.services.BeerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/beers")
public class BeerController {

    private final BeerService beerService;
    private final BeerMapper beerMapper;
    private final AuthenticationHelper authenticationHelper;


    @Autowired
    public BeerController(BeerService beerService, BeerMapper beerMapper, AuthenticationHelper authenticationHelper) {

        this.beerService = beerService;
        this.beerMapper = beerMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Beer> getBeers(@RequestParam(required = false) String name,
                               @RequestParam(required = false) Double minAbv,
                               @RequestParam(required = false) Double maxAbv,
                               @RequestParam(required = false) Integer styleId,
                               @RequestParam(required = false) String sortBy,
                               @RequestParam(required = false) String orderBy) {
        FilterOptions filterOptions = new FilterOptions(name, minAbv, maxAbv, styleId, sortBy, orderBy);

        return beerService.getAll(filterOptions);
    }


    @GetMapping("/{id}")
    public Beer getBeer(@PathVariable int id) {
        try {
            return beerService.getBeerById(id);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/search")
    public Beer getByName(@RequestParam String name) {
        try {
            return beerService.getBeerByName(name);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Beer> createBeer(@RequestHeader HttpHeaders header, @Valid @RequestBody BeerDto beerDTO) {
        try {
            User beerCreator = authenticationHelper.tryGetUser(header);
            Beer beer = beerMapper.fromDto(beerDTO);
            Beer theNewBeer = beerService.create(beer, beerCreator);
            return ResponseEntity.status(HttpStatus.CREATED).body(theNewBeer);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedAccessException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Beer updateBeer(@RequestHeader HttpHeaders header, @Valid @RequestBody BeerDto beerDTO, @PathVariable int id) {

        try {
            User user = authenticationHelper.tryGetUser(header);
            Beer beer = beerMapper.fromDto(id, beerDTO);
            beerService.update(beer, user);
            return beer;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedAccessException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBeer(@RequestHeader HttpHeaders header, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(header);
            beerService.delete(id, user);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (UnauthorizedAccessException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
