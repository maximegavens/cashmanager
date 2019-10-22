package com.manager.cash.service;

import com.manager.cash.model.Server;
import com.manager.cash.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    private List<User> userList;

    public UserService() {
        this.userList = new ArrayList<User>(Arrays.asList(new User("pierre", "admin", "pierre", 100, "123", "456"),
                new User("paul", "admin", "paul", 100, "123", "456"),
                new User("jack", "admin", "jack", 100, "123", "456")));

    }

    public boolean save(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        userList.add(user);
        return true;
    }

    public List<User> getAll() {
        return userList;
    }

    public boolean checkAuth(String login, String password) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getLogin().equals(login) && userList.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


}
