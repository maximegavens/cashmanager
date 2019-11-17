package com.cashmanager.service;

import com.cashmanager.model.User;
import com.cashmanager.repository.SettingAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SettingAdminService {

    @Autowired
    SettingAdminRepository repository;

    public List<User> getAllUser() {
        List<User> listuser = repository.findAll();
        if (listuser.size() > 0)
            return listuser;
        else
            return new ArrayList<User>();
    }

    public User getUserById(long id) {
        Optional<User> user = repository.findById(id);
        return user.get(); //please add exception
    }

    public User createUser(User us) {
        Optional<User> user = repository.findById(us.getId_user());
        System.out.println(us);

        if (!user.isPresent()) {
            us = repository.save(us);
            return (us);
        }
        return null;
    }

    public Boolean UpdateUser(long id, User us) {
        if (repository.existsById(id)) {
            User user = repository.getOne(id);
            user.setFull_name(us.getFull_name());
            user.setEmail(us.getEmail());
            user.setPassword(us.getPassword());
            repository.save(user);
            return true;
        }
        return false;
    }

    public void deleteUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent())
            repository.deleteById(id);
    }
}
