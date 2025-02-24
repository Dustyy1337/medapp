package com.medstat.model;

public class User {
    private String username, password;
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
