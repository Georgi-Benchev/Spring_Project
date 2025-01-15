package com.example.spring_demo.repositorys;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Style;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class StyleRepositoryImpl implements StyleRepository {

    private final SessionFactory sessionFactory;


    @Autowired
    public StyleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Style> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Style", Style.class).list();
        }

    }

    @Override
    public Style getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Style style = session.get(Style.class, id);
            if (style == null) {
                throw new EntityNotFoundException("Style", id);
            }
            return style;
        }
    }
}
