package com.example.spring_demo.services;

import com.example.spring_demo.models.Style;
import com.example.spring_demo.repositorys.StyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StyleServiceImpl implements StyleService {

    private final StyleRepository repository;

    @Autowired
    public StyleServiceImpl(StyleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Style> getAll() {
        return repository.getAll();
    }

    @Override
    public Style getById(int id) {
        return repository.getById(id);
    }
}
