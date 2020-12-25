package com.cursor.model;

public class User {
    private static int id;
    private String password;
    private String username;

    public User() {
        id++;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        id++;
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

    public static int getId() {
        return id;
    }
}
