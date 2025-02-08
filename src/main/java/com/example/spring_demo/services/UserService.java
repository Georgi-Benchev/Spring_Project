package com.example.spring_demo.services;

import com.example.spring_demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(int id);

    User getByUsername(String username);

    void createUser(User user);
}