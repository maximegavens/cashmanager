package com.manager.cash.model;

import org.joda.time.DateTime;

public class Server {
    private int id;
    private String password;
    private String status;
    private String lastSignIn;

    public Server() {
        this.id = -1;
        this.password = "";
        this.status = "refused";
        DateTime dt = new DateTime();
        this.lastSignIn = dt.toString();
    }
    public Server(int id, String password) {
        this.id = id;
        this.password = password;
        this.status = "refused";
        DateTime dt = new DateTime();
        this.lastSignIn = dt.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastSignIn() { return lastSignIn; }

    public void setLastSignIn(String lastSignIn) { this.lastSignIn = lastSignIn; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
