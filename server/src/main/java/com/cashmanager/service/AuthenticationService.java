package com.cashmanager.service;

import com.cashmanager.model.User;
import com.cashmanager.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationRepository repository;

    public Boolean SignUp(User us) {
        if (repository.existsUserByEmail(us.getEmail())) {
            User user = repository.findUserByEmail(us.getEmail());
                if (user.getPassword().equals(us.getPassword()) == true)
                    return true;
        }
        return false;
    }

    public String createUser(User us) {
        if (!repository.existsUserByEmail(us.getEmail())) {
            User user = new User();
            user.setFull_name(us.getFull_name());
            user.setEmail(us.getEmail());
            user.setPassword(us.getPassword());
            repository.save(user);
            return "Success creating";
        }
        return "Error: This user already exists";
    }
}
