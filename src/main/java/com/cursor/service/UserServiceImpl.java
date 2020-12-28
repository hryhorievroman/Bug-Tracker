package com.cursor.service;

import com.cursor.model.User;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.dao.impls.UserDao;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao users = UserDao.getInstance();

    @Override
    public void registerUser(User user) {
        checkExistence(user.getUsername());
        if (user.getUsername().isBlank() || (user.getPassword().isBlank())) {
            throw new BadRequestException("Invalid username or password");
        } else if (user.getPassword().length() < 8) {
            throw new BadRequestException("The password is too short");
        } else {
            users.create(user);
        }
    }

    @Override
    public void loginUser(String username, String password) {
        checkExistence(username, password);
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
        checkExistence(id);
        System.out.println(users.findById(id).toString());
        return users.findById(id);

    }

    @Override
    public void edit(int id, User entity) {
        checkExistence(id);
        if (!users.edit(id, entity)
                || entity.getUsername().isBlank() || entity.getPassword().isBlank()) {
            throw new BadRequestException("Invalid user data");
        }
    }

    @Override
    public void delete(int id) {
        checkExistence(id);
        users.delete(id);
    }

    private void checkExistence(int id) {
        if (users.findById(id) == null) {
            throw new NotFoundException("The user was not found");
        }
    }

    private void checkExistence(String userName) {
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName)) {
                throw new BadRequestException("User already exists");
            }
        }
    }

    private void checkExistence(String userName, String password) {
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                return;
            }
        }
        throw new BadRequestException("Username or password is incorrect");
    }
}
