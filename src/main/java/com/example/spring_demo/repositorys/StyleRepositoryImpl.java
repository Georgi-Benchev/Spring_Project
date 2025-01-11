package com.example.spring_demo.repositorys;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Style;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StyleRepositoryImpl implements StyleRepository {

    private List<Style> styles;

    public StyleRepositoryImpl(List<Style> styles) {
        this.styles = styles;

        styles.add(new Style(1, "Cold brew"));
        styles.add(new Style(2, "Ale"));
        styles.add(new Style(3, "Dark"));
    }

    @Override
    public List<Style> getAll() {
        return styles;
    }

    @Override
    public Style getById(int id) {
        return styles.stream().filter(style -> style.getId() == id).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Style", "id", String.valueOf(id)));

    }
}
