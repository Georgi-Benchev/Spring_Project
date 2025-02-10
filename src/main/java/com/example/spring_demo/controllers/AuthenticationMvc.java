package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.DuplicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.helpers.UserMapper;
import com.example.spring_demo.models.Dtos.LoginRequest;
import com.example.spring_demo.models.Dtos.RegistrationRequest;
import com.example.spring_demo.models.User;
import com.example.spring_demo.services.UserService;
import jakarta.servlet.http.HttpSession;
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
    public String executeLoginRequest(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest, BindingResult errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "Login";
        }
        try {
            User user = userService.getByUsername(loginRequest.getUsername());
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new EntityNotFoundException();
            }
            session.setAttribute("currentUser", loginRequest.getUsername());
            return "redirect:/home";
        } catch (EntityNotFoundException e) {
            errors.rejectValue("username", "username.mismatch", "invalid username or password");
            return "Login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        try {
            session.invalidate();
        }catch (Exception ignored){

        }
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("RegistrationRequest", new RegistrationRequest());
        return "Registration";
    }

    @PostMapping("/register")
    public String executeRegistrationRequest(@Valid @ModelAttribute("RegistrationRequest") RegistrationRequest registrationRequest, BindingResult errors) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatPassword())) {
            errors.rejectValue("repeatPassword", "passwords.mismatch", "The password was incorrect");
        }
        if (errors.hasErrors()) {
            return "Registration";
        }
        try {
            userService.createUser(userMapper.getUserFromDto(registrationRequest));
            return "Login";
        } catch (DuplicateEntityException e) {
            errors.rejectValue("username", "username.mismatch", "Username already taken");
            return "Registration";
        }
    }
}