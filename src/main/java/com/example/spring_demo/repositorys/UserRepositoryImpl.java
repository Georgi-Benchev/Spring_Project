package com.example.spring_demo.repositorys;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    List<User> users;

    public UserRepositoryImpl() {
        users = new ArrayList<>();

        users.add(new User(1, "Pesho", true));
        users.add(new User(2, "Gosho", true));
        users.add(new User(3, "Vanko", false));
        users.add(new User(4, "Danko", false));
    }


    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User getById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", "id", String.valueOf(id)));
    }

    @Override
    public User getByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }
}
