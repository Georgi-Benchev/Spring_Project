package com.example.spring_demo.controllers;

import com.example.spring_demo.models.LoginRequest;
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

    @Autowired
    public AuthenticationMvc(UserService userService) {
        this.userService = userService;
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
        //todo validate username and password and return cookie

        return "HomeView";


    }


}
