package com.cursor.service;

import com.cursor.dao.impls.UserDaoDb;
import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.User;
import com.cursor.model.enums.Size;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.dao.impls.UserDaoInMem;
import com.cursor.service.exceptions.ErrorMessage;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final CRUD<User> users = UserDaoDb.getInstance();
//    private final CRUD<User> users = UserDaoInMem.getInstance();

    @Override
    public void registerUser(User user) {
        checkExistence(user.getUsername());
        if (user.getPassword().length() < Size.PASSWORD_MIN_LENGTH.getSize() || user.getUsername().length() < Size.USERNAME_MIN_LENGTH.getSize()) {
            throw new BadRequestException("The username/password is too short");
        } else {
            users.create(user);
        }
    }

    @Override
    public User loginUser(String username, String password) {
        return checkExistence(username, password);
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
        return users.findById(id);
    }

    @Override
    public void edit(int id, User entity) {
        checkExistence(id);
        if (!users.edit(id, entity)
                || entity.getPassword().length() < Size.PASSWORD_MIN_LENGTH.getSize() || entity.getUsername().length() < Size.USERNAME_MIN_LENGTH.getSize()) {
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
            throw new NotFoundException(ErrorMessage.NOT_FOUND.getErrorMessage());
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

    private User checkExistence(String userName, String password) {
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new BadRequestException("Username or password is incorrect");
    }
}
