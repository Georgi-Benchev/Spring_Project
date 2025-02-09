package com.example.spring_demo.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RegistrationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String repeatPassword;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String first_name;

    @NotEmpty
    private String last_name;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String username, String password, String passwordRepeat, String email, String first_name, String last_name) {
        this.username = username;
        this.password = password;
        this.repeatPassword = passwordRepeat;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
