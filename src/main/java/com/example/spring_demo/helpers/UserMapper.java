package com.example.spring_demo.helpers;


import com.example.spring_demo.models.Dtos.RegistrationRequest;
import com.example.spring_demo.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User getUserFromDto(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setAdmin(false);
        user.setFirstName(registrationRequest.getFirst_name());
        user.setLastName(registrationRequest.getLast_name());
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setEmail(registrationRequest.getEmail());
        return user;
    }
}
