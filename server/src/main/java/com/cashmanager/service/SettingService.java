package com.cashmanager.service;

import com.cashmanager.model.User;
import com.cashmanager.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SettingService {

    @Autowired
    SettingRepository repository;

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

    public User UpdateUser(User us) {
        Optional<User> user = repository.findById(us.getId_user());

        if (user.isPresent()) {
            User newuser = user.get();
            newuser.setFull_name(us.getFull_name());
            newuser.setEmail(us.getEmail());
            newuser.setPassword(us.getPassword());

            newuser = repository.save(us);
            return newuser;
        }
        return us;
    }

    public void deleteUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent())
            repository.deleteById(id);
    }
}
