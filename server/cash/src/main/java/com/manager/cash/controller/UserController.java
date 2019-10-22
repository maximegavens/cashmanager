package com.manager.cash.controller;

import com.manager.cash.model.Server;
import com.manager.cash.model.User;
import com.manager.cash.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.manager.cash.service.UtilService.log;

@CrossOrigin(origins="*", value = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> all() {
        List<User> listUser = userService.getAll();
        ResponseEntity<List<User>> rp = new ResponseEntity<>(listUser, HttpStatus.OK);
        return rp;
    }

    @PostMapping("/users")
    ResponseEntity<?> newUser(@RequestBody User user) {
        log(user);
        boolean resp = userService.save(user);

        if (resp) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }




/*    @PostMapping("/order/:id")
    ResponseEntity upadteOrder(@RequestBody Map<String, Object> orderParam) {
        log(orderParam);

    }*/
}
