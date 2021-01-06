package com.cursor.service;

import com.cursor.dao.impls.UserDaoDb;
import com.cursor.model.User;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
//    private final UserDao users = UserDao.getInstance();
    private final UserDaoDb users = UserDaoDb.getInstance();

    @Override
    public void registerUser(User user) throws SQLException, ClassNotFoundException {
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
    public void loginUser(String username, String password) throws SQLException, ClassNotFoundException {
        checkExistence(username, password);
    }

    @Override
    public void create(User entity) throws SQLException, ClassNotFoundException {
        if (!users.create(entity)) {
            throw new BadRequestException("An error while creating user occurred");
        }
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return users.getAll();
    }

    @Override
    public User findById(int id) throws SQLException, ClassNotFoundException {
        checkExistence(id);
        System.out.println(users.findById(id).toString());
        return users.findById(id);
    }

    @Override
    public void edit(int id, User entity) throws SQLException, ClassNotFoundException {
        checkExistence(id);
        if (!users.edit(id, entity)
                || entity.getUsername().isBlank() || entity.getPassword().isBlank()) {
            throw new BadRequestException("Invalid user data");
        }
    }

    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {
        checkExistence(id);
        users.delete(id);
    }

    private void checkExistence(int id) throws SQLException, ClassNotFoundException {
        if (users.findById(id) == null) {
            throw new NotFoundException("The user was not found");
        }
    }

    private void checkExistence(String userName) throws SQLException, ClassNotFoundException {
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName)) {
                throw new BadRequestException("User already exists");
            }
        }
    }

    private void checkExistence(String userName, String password) throws SQLException, ClassNotFoundException {
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                return;
            }
        }
        throw new BadRequestException("Username or password is incorrect");
    }
}
