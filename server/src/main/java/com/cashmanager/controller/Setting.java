package com.cashmanager.controller;

import com.cashmanager.model.User;
import com.cashmanager.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/setting")
public class Setting {

    @Autowired
    SettingService service;

    /*@GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list_user = service.getAllUser();
        return new ResponseEntity<List<User>>(list_user, new HttpHeaders(), HttpStatus.OK);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = service.getUserById(id);
        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<User> updateUser( @PathVariable("id") long id, User us ) {
        us.setId_user(id);
        User user = service.UpdateUser(us);
        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable("id") long id) {
        service.deleteUser(id);
        return HttpStatus.FORBIDDEN;
    }
}
