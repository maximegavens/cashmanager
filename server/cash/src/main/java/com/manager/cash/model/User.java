package com.manager.cash.model;

public class User {
    private String login;
    private String password;
    private String username;
    private float  cash;
    private String QRcode;
    private String NFC;

    public User() {}

    public User(String login, String password, String username, float cash, String QRcode, String NFC) {
        this.login = login;
        this.password = password;
        this.username = username;
        this.cash = cash;
        this.QRcode = QRcode;
        this.NFC = NFC;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }

    public String getNFC() {
        return NFC;
    }

    public void setNFC(String NFC) {
        this.NFC = NFC;
    }
}
