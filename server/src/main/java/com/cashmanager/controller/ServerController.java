package com.cashmanager.controller;

import com.cashmanager.model.Server;
import com.cashmanager.model.User;
import com.cashmanager.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    ServerService service;

    @GetMapping
    public ResponseEntity<List<Server>> getAllServer() {
        List<Server> Server = service.getAllServer();
        return new ResponseEntity<List<Server>>(Server, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Server> getServerById(@PathVariable("id") long id) {
        Server server = service.getServerById(id);
        return new ResponseEntity<Server>(server, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<Server> signInServer( Server s ) {
        Server server = service.checkAuth(s);
        return new ResponseEntity<Server>(server, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Server> createServer( Server s ) {
        Server server = service.createServer(s);
        return new ResponseEntity<Server>(server, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Server> updateServer( Server s ) {
        Server server = service.UpdateServer(s);
        return new ResponseEntity<Server>(server, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteServer(@PathVariable("id") long id) {
        service.deleteServer(id);
        return HttpStatus.FORBIDDEN;
    }
}
