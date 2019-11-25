package com.cashmanager.service;

import com.cashmanager.model.User;
import com.cashmanager.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationRepository repository;

    public User SignIn(String login, String password) {
        if (repository.existsUserByEmail(login)) {
            User user = repository.findUserByEmail(login);
            if (user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public User createUser(User us) {
        if (!repository.existsUserByEmail(us.getEmail())) {
            User user = new User();
            user.setFull_name(us.getFull_name());
            user.setEmail(us.getEmail());
            user.setPassword(us.getPassword());
            return repository.save(user);
        }
        return null;
    }
}
