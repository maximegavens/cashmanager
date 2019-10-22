package com.manager.cash.service;

import com.manager.cash.model.Server;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerService {
    private List<Server> serverList;

    private ServerService() {
        this.serverList = new ArrayList<Server>(Arrays.asList(
                new Server(123, "admin"),
                new Server(456, "admin")
        ));
    }

    private static ServerService INSTANCE = null;

    public static synchronized ServerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerService();
        }
        return INSTANCE;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

    public Server getServerById(int id) {
        for (int i = 0; i < this.serverList.size(); i++) {
            if (this.serverList.get(i).getId() == id) {
                return this.serverList.get(i);
            }
        }
        return null;
    }

    public boolean checkAuthServer(int id, String password) {
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).getId() ==  id && serverList.get(i).getPassword().equals(password)) {
                DateTime dt = new DateTime();
                this.serverList.get(i).setLastSignIn(dt.toString());
                return true;
            }
        }
        return false;
    }
}
