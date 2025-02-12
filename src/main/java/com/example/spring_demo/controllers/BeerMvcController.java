package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.DuplicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.helpers.AuthenticationHelper;
import com.example.spring_demo.helpers.BeerMapper;
import com.example.spring_demo.models.*;
import com.example.spring_demo.models.Dtos.FilterOptionsDto;
import com.example.spring_demo.services.BeerService;
import com.example.spring_demo.services.StyleService;
import com.example.spring_demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/mvc/beers")
public class BeerMvcController {

    private final BeerService beerService;
    private final UserService userService;
    private final StyleService styleService;
    private final BeerMapper beerMapper;
    private final AuthenticationHelper authenticationHelper;

    public BeerMvcController(BeerService beerService, UserService userService, StyleService styleService, BeerMapper beerMapper, AuthenticationHelper authenticationHelper) {
        this.beerService = beerService;
        this.userService = userService;
        this.styleService = styleService;
        this.beerMapper = beerMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public ModelAndView getAllBeers(@ModelAttribute("filterOptions") FilterOptionsDto filterOptionsDto,BindingResult errors) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            return modelAndView;
        }
        FilterOptions filterOptions = new FilterOptions( filterOptionsDto.getName(),
                filterOptionsDto.getMinAbv(),
                filterOptionsDto.getMaxAbv(),
                filterOptionsDto.getStyleId(),
                filterOptionsDto.getSortBy(),
                filterOptionsDto.getOrderBy());
        List<Beer> beers = beerService.getAll(filterOptions);
        modelAndView.addObject("beers", beers);
        modelAndView.setViewName("BeersView");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getBeerById(@PathVariable int id, HttpSession session) {
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

    @GetMapping("/new")
    public String createFormBeer(Model model, HttpSession session) {

        if (getCurrentUser(session).getUsername().equals("Anonymous User")) {
            return "redirect:/auth/login";
        }

        model.addAttribute("beer", new BeerDto());
        return "CreateBeer";
    }

    @PostMapping("/new")
    public String saveBeer(@Valid @ModelAttribute("beer") BeerDto beerDto, BindingResult errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "CreateBeer";
        }
        try {
            User user = authenticationHelper.tryGetUser(session);
            Beer beer = beerMapper.fromDto(beerDto);

            beerService.create(beer, user);
            return "redirect:/mvc/beers";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("name", "duplicate", e.getMessage());
            return "CreateBeer";
        }
    }


    @GetMapping("/{id}/update")
    public String getUpdateBeerPage(@PathVariable int id, Model model, HttpSession session) {
        if (getCurrentUser(session).getUsername().equals("Anonymous User")) {
            return "redirect:/auth/login";
        }
        try {
            User user = getCurrentUser(session);
            Beer beer = beerService.getBeerById(id);
            if (!user.isAdmin() && !user.getUsername().equals(beer.getCreatedBy().getUsername())) {
                return "Forbidden";
            }

            BeerDto beerDto = beerMapper.toDto(beer);
            model.addAttribute("beer", beerDto);
            return "UpdateBeer";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "Not-found";
        }
    }


    @PostMapping("/{id}/update")
    public String updateBeer(@Valid @ModelAttribute("beer") BeerDto beerDto, BindingResult errors, @PathVariable int id, Model model) {
        if (errors.hasErrors()) {
            return "UpdateBeer";
        }
        try {
            User user = userService.getById(3);
            Beer beer = beerMapper.fromDto(id, beerDto);
            beerService.update(beer, user);
            return "redirect:/mvc/beers";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("name", "duplicate", e.getMessage());
            return "UpdateBeer";
        }
    }


    @GetMapping("/{id}/delete")
    public String deleteBeer(@PathVariable int id, Model model, HttpSession session) {
        if (getCurrentUser(session).getUsername().equals("Anonymous User")) {
            return "redirect:/auth/login";
        }
        try {
            User user = getCurrentUser(session);
            Beer beer = beerService.getBeerById(id);
            if (!user.isAdmin() && !user.getUsername().equals(beer.getCreatedBy().getUsername())) {
                return "Forbidden";
            }

            beerService.delete(id, user);
            return "redirect:/mvc/beers";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "Not-found";
        }
    }
    //
    //
    //
    //
    //

    @ModelAttribute("styles")
    public List<Style> populateStyles() {
        return styleService.getAll();
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("user")
    public User getCurrentUser(HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (Exception e) {
            user = new User();
            user.setAdmin(false);
            user.setUsername("Anonymous User");
        }
        return user;
    }
}