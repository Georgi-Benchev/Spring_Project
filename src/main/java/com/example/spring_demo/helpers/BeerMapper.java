package com.example.spring_demo.helpers;

import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.BeerDto;
import com.example.spring_demo.repositorys.BeerRepository;
import com.example.spring_demo.repositorys.StyleRepository;
import com.example.spring_demo.repositorys.StyleRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeerMapper {

    private final StyleRepository styleRepositoryImpl;
    private final BeerRepository beerRepository;

    @Autowired
    public BeerMapper(BeerRepository beerRepository, StyleRepositoryImpl styleRepositoryImpl) {
        this.beerRepository = beerRepository;
        this.styleRepositoryImpl = styleRepositoryImpl;
    }


    public Beer fromDto(int id, BeerDto dto) {
        Beer beer = fromDto(dto);
        beer.setId(id);
        beer.setCreatedBy(beerRepository.getBeerById(id).getCreatedBy());
        return beer;
    }

    public Beer fromDto(BeerDto dto) {
        Beer beer = new Beer();
        beer.setName(dto.getName());
        beer.setAbv(dto.getAbv());
        beer.setStyle(styleRepositoryImpl.getById(dto.getStyleId()));
        return beer;
    }
}
