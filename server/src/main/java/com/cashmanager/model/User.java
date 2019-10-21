package com.cashmanager.model;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class User {
    private int id_user;
    private String full_name;
    private String email;
    private String Password;
    private boolean connected;

    public User() {
        this.connected = false;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected() {
        this.connected = true;
    }

    public void setDeconnected() {
        this.connected = false;
    }
}
