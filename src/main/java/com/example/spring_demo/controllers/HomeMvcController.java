package com.example.spring_demo.controllers;

import com.example.spring_demo.helpers.AuthenticationHelper;
import com.example.spring_demo.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeMvcController {

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public HomeMvcController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String getHomepage(Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute("isUserLoggedIn", true);
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("isUserLoggedIn", false);
        }
        return "HomeView";
    }
}
