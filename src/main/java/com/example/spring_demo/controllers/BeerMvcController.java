package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.FilterOptions;
import com.example.spring_demo.services.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/mvc/beers")
public class BeerMvcController {

    private final BeerService beerService;

    @Autowired
    public BeerMvcController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{id}")
    public ModelAndView getBeerById(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        try {

            Beer beer = beerService.getBeerById(id);

            modelAndView.addObject("beer", beer);
            modelAndView.setViewName("BeerView");

            return modelAndView;
        } catch (EntityNotFoundException e) {

            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Not-found");
            return modelAndView;
        }
    }

    @GetMapping
    public ModelAndView getAllBeers() {
        ModelAndView modelAndView = new ModelAndView();

        FilterOptions filterOptions = new FilterOptions(null, null, null, null, null, null);
        List<Beer> beers = beerService.getAll(filterOptions);

        modelAndView.addObject("beers", beers);

        modelAndView.setViewName("BeersView");

        return modelAndView;

    }


}
