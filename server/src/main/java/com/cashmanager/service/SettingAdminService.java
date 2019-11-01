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

    public Boolean UpdateUser(User us) {
        if (repository.existsById(us.getId_user())) {
            User user = repository.getOne(us.getId_user());
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
