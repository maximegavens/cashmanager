package com.cashmanager.service;

import com.cashmanager.model.Server;
import com.cashmanager.repository.ServerRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServerService {

    @Autowired
    ServerRepository repository;

    public List<Server> getAllServer() {
        List<Server> listserver = repository.findAll();
        if (listserver.size() > 0)
            return listserver;
        else
            return new ArrayList<Server>();
    }

    public Server getServerById(long id) {
        Optional<Server> server = repository.findById(id);
        return server.get();
    }

    public Server checkAuth(Server s) {
        Optional<Server> server = repository.findById(s.getId_server());
        System.out.println(s);

        if(server.isPresent()) {
            String password = server.get().getPassword();
            if (password.equals(s.getPassword())) {
                return server.get();
            }
        }
        return null;
    }

    public Server createServer(Server s) {
        Optional<Server> server = repository.findById(s.getId_server());
        System.out.println(s);

        if (!server.isPresent()) {
            DateTime dt = new DateTime();
            s.setLast_connection(dt.toString());
            s.setStatus("REFUSED");
            s = repository.save(s);
            return (s);
        }
        return null;
    }

    public Server UpdateServer(Server s) {
        Optional<Server> server = repository.findById(s.getId_server());

        if (server.isPresent()) {
            DateTime dt = new DateTime();
            Server newserver = server.get();
            newserver.setPassword(s.getPassword());
            newserver.setStatus(s.getStatus());
            newserver.setLast_connection(dt.toString());

            newserver = repository.save(s);
            return newserver;
        }
        return null;
    }

    public void deleteServer(Long id) {
        Optional<Server> server = repository.findById(id);
        if (server.isPresent())
            repository.deleteById(id);
    }
}
