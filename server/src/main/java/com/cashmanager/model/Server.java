package com.cashmanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "server")
public class Server {
    @Id
    @GeneratedValue(generator = "id_generator") //@GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_generator", sequenceName = "id_sequence", initialValue = 100)
    @Column(name = "id_server", nullable = false, updatable = false)
    private long id_server;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "last_connection")
    private String last_connection;

    public long getId_server() {
        return id_server;
    }

    public void setId_server(long id_server) {
        this.id_server = id_server;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_connection() {
        return last_connection;
    }

    public void setLast_connection(String last_connection) {
        this.last_connection = last_connection;
    }
}
