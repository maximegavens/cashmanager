package com.cashmanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "userapp")
public class User {

    @Id
    @GeneratedValue(generator = "id_generator") //@GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_generator", sequenceName = "id_sequence", initialValue = 100)
    @Column(name = "id_user", nullable = false, updatable = false)
    private long id_user;

    @Column(name = "full_name")
    @NotBlank(message = "Name is mandatory")
    private String full_name;

    @Column(name = "email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    //public User(int id_user) {
    //    this.id_user = id_user;
    //}

    public long getId_user() {
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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
