package com.easySMS.model;

/**
 * Created by poiuyt on 10/21/16.
 */

public class Member {

    String username;
    String password;
    String email;
    String created;
    String connection;

    public Member(String username, String email, String password, String created, String connection ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.created = created;
        this.connection = connection;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

}
