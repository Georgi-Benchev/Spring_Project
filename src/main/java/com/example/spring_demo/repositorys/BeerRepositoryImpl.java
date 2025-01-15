package com.example.spring_demo.repositorys;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
@Primary
public class BeerRepositoryImpl implements BeerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public BeerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Beer> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Beer", Beer.class).list();
        }
    }

    @Override
    public Beer getBeerById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Beer beer = session.get(Beer.class, id);
            if (beer == null) {
                throw new EntityNotFoundException("Beer", id);
            }
            return beer;
        }
    }

    @Override
    public Beer getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Beer> beers = session.createQuery("From Beer Where name = :name", Beer.class);
            beers.setParameter("name", name);
            return beers
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Beer", "name", name));
        }
    }

    @Override
    public void create(Beer beer, User user) { // remove user from interface
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(beer);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Beer beer, int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(beer);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(getBeerById(id));
            session.getTransaction().commit();
        }
    }
}
