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

<<<<<<< HEAD
    @PostMapping("/signup")
    public boolean SignUpForm(@RequestBody User user) {
        return service.SignUp(user);
=======
    @PostMapping("/sign_in")
    public ResponseEntity<User> SignIn(@RequestParam(value = "login") String login, @RequestParam(value = "password") String password) {
        User user = service.checkAuth(login, password);
        if (user != null) {
            return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<User>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
>>>>>>> 39b1445ad63e5c32a91e238dabc5fa3b538a2786
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
