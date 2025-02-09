package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.DuplicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.helpers.UserMapper;
import com.example.spring_demo.models.LoginRequest;
import com.example.spring_demo.models.RegistrationRequest;
import com.example.spring_demo.models.User;
import com.example.spring_demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationMvc {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationMvc(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "Login";
    }

    @PostMapping("/login")
    public String executeLoginRequest(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            return "Login";
        }
        try {
            User user = userService.getByUsername(loginRequest.getUsername());
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new EntityNotFoundException();
            }
            return "HomeView";
        } catch (EntityNotFoundException e) {
            errors.rejectValue("username", "username.mismatch", "invalid username or password");
            errors.rejectValue("password", "password.mismatch", "invalid username or password");
            return "Login";
        }
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("RegistrationRequest", new RegistrationRequest());
        return "Registration";
    }

    @PostMapping("/register")
    public String executeRegistrationRequest(@Valid @ModelAttribute("RegistrationRequest") RegistrationRequest registrationRequest, BindingResult errors) {
        //todo validate username and password and return cookie
        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatPassword())) {
            errors.rejectValue("repeatPassword", "passwords.mismatch", "The password was incorrect");
        }
        if (errors.hasErrors()) {
            return "Registration";
        }
        try {
            userService.createUser(userMapper.getUserFromDto(registrationRequest));
        } catch (DuplicateEntityException e) {
            errors.rejectValue("username", "username.mismatch", "Username already taken");
            return "Registration";
        }

        return "HomeView";
    }


}