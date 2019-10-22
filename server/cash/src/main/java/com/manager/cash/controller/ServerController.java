package com.manager.cash.controller;

import com.manager.cash.model.Server;
import com.manager.cash.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.manager.cash.service.UtilService.log;

@CrossOrigin(origins="*", value = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
public class ServerController {
    private ServerService serverService = ServerService.getInstance();

    @PostMapping(path = "/server/sign_in", consumes = "application/x-www-form-urlencoded")
    ResponseEntity login(@RequestParam("id") String idParam, @RequestParam("password") String passwordParam) {
        int id = Integer.parseInt((String) idParam);

        if (serverService.checkAuthServer(id, passwordParam)) {
            Server server = serverService.getServerById(id);
            return new ResponseEntity<>(server, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Server)null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/server/all")
    ResponseEntity getServers() {
        List<Server> servers = serverService.getServerList();
        return new ResponseEntity<>(servers, HttpStatus.OK);
    }

    @GetMapping("/server/{id}")
    ResponseEntity getServerById(@PathVariable int id) {
        Server server = serverService.getServerById(id);
        return new ResponseEntity<>(server, HttpStatus.OK);
    }
}
