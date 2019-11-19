package com.cashmanager.controller;

import com.cashmanager.model.User;
import com.cashmanager.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Authentication {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/signin")
    public ResponseEntity<User> SignIn(@RequestBody User user) {
        User us = service.SignIn(user);
        if (us != null) {
            return new ResponseEntity<User>(us, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<User>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User us) {
        User user = service.createUser(us);
        if (user != null) {
            return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<User>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
