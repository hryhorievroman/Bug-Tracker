package com.cursor.model;

public class User {
    private int id;
    private static int idGenerator = 0;
    private String password;
    private String username;

    public User() {
        this.id = ++idGenerator;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = ++idGenerator;
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
