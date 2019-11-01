package com.cashmanager.service;

import com.cashmanager.model.User;
import com.cashmanager.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationRepository repository;

    public String SignUp(User us) {
        Optional<User> user = repository.findById(us.getId_user());

        if (user.isPresent()) {
            if (user.get().getPassword() == us.getPassword())
                return "Authentication Success";
        }
        return "Authentication Failed";
    }

    public String createUser(User us) {
        Optional<User> user = repository.findById(us.getId_user());

        if (!user.isPresent()) {
            repository.save(us);
            return "Success creating";
        }
        return "Error: This user already exists";
    }
}
