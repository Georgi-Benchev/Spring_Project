package com.example.spring_demo.helpers;

import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.exceptions.UnauthorizedAccessException;
import com.example.spring_demo.models.User;
import com.example.spring_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationHelper {
    private static final String AUTHORIZATION = "Authorization";
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION)) {
            throw new UnauthorizedAccessException("Invalid authentication(you have no access)");
        }

        try {
            return userService.getByUsername(headers.getFirst(AUTHORIZATION));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("User", "username", headers.getFirst(AUTHORIZATION));
        }
    }
}
