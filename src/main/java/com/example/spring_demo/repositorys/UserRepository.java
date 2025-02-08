package com.example.spring_demo.repositorys;

import com.example.spring_demo.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User getById(int id);

    User getByUsername(String username);

    void createUser(User user);
}