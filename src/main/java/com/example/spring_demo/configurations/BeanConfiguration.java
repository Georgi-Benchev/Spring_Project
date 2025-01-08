package com.example.spring_demo.configurations;

import com.example.spring_demo.repositorys.BeerRepository;
import com.example.spring_demo.repositorys.BeerRopositoryImpl;
import com.example.spring_demo.services.BeerService;
import com.example.spring_demo.services.BeerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
@Configuration
public class BeanConfiguration {

    @Bean
   public BeerRepository beerRepository(){
       return new BeerRopositoryImpl();
   }

   @Bean
    public BeerService beerService(){
        return new BeerServiceImpl(beerRepository());
    }
}*/
