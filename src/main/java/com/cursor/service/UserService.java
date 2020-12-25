package com.cursor.service;

import com.cursor.model.User;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.dao.interfaces.CRUD;
import com.cursor.dao.impls.UserDao;
import com.cursor.service.exceptions.NotFoundException;

import java.util.List;

public class UserService implements com.cursor.service.interfaces.UserService {

    CRUD<User> users = new UserDao();

    @Override
    public void registerUser(User user) {
        if (usersContain(user.getUsername(), user.getPassword()) || user.getUsername().isBlank() || user.getPassword().isBlank()) {
            throw new BadRequestException("Invalid username or password");
        }
        else {
            users.create(user);
        }
    }

    @Override
    public void loginUser(String username, String password) {
        if (!usersContain(username, password)) {
            throw new BadRequestException("Username or password is incorrect");
        }
    }

    @Override
    public void create(User entity) {
        if (!users.create(entity)) {
            throw new BadRequestException("An error while creating user occurred");
        }
    }

    @Override
    public List<User> getAll() {
        return users.getAll();
    }

    @Override
    public User findById(int id) {
        ifExists(id);
        return users.findById(id);
    }

    @Override
    public void edit(int id, User entity) {
        ifExists(id);
        if (!users.edit(id, entity) || entity.getUsername().isBlank() || entity.getPassword().isBlank()) {
            throw new BadRequestException("Invalid user data");
        }
    }

    @Override
    public void delete(int id) {
        ifExists(id);
        users.delete(id);
    }

    private void ifExists(int id) { // check if user exists and throws exception if not
        if (users.findById(id) == null) {
            throw new NotFoundException("The user was not found");
        }
    }

    private boolean usersContain(String userName, String password) { // check if the user with inputted username and password exists in userDao
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
