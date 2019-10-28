package com.cashmanager.controller;

import com.cashmanager.model.User;
import com.cashmanager.repository.UserRepository;
import com.cashmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class authentification {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list_user = service.getAllUser();
        return new ResponseEntity<List<User>>(list_user, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = service.getUserById(id);
        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser( User us ) {
        User user = service.createUser(us);
        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
        return HttpStatus.FORBIDDEN;
    }

    /*@Autowired
    private UserRepository usRepository;

    @GetMapping("/auth")
    public Page<User> login(Pageable pageable) {
        return usRepository.findAll(pageable);
    }

    @PostMapping("/register")
    public User register() {
        User us = new User();
        us.setId_user(999);
        us.setFull_name("Mavrick");
        us.setEmail("mavrick@test.fr");
        us.setPassword("testtest");
        return usRepository.save(us);
    }*/
}
