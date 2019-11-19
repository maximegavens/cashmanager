package com.cashmanager.service;

import com.cashmanager.model.User;
import com.cashmanager.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationRepository repository;

<<<<<<< HEAD
    public Boolean SignUp(User us) {
        if (repository.existsUserByEmail(us.getEmail())) {
            User user = repository.findUserByEmail(us.getEmail());
                if (user.getPassword().equals(us.getPassword()) == true)
                    return true;
        }
        return false;
=======
    public User checkAuth(String email, String password) {
        if (repository.existsUserByEmail(email)) {
            User user = repository.findUserByEmail(email);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
>>>>>>> 39b1445ad63e5c32a91e238dabc5fa3b538a2786
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
