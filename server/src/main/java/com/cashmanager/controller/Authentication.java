package com.cashmanager.controller;

import com.cashmanager.model.User;
import com.cashmanager.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Authentication {

    @Autowired
    AuthenticationService service;

    @PostMapping("/signup")
    public String SignUpForm(User user) {
        return service.SignUp(user);
    }

    @PostMapping("/register")
    public String createUser(User us) {
        return service.createUser(us);
    }
}
