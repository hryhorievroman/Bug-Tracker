package com.cursor.service;

import com.cursor.dao.impls.UserDaoDb;
import com.cursor.model.User;
import com.cursor.model.enums.Size;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.dao.impls.UserDaoInMem;
import com.cursor.service.exceptions.ErrorMessage;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDaoDb users = UserDaoDb.getInstance();
//    private final UserDaoInMem users = UserDaoInMem.getInstance();

    @Override
    public void registerUser(User user) throws SQLException {
        checkExistence(user.getUsername());
        if (user.getPassword().length() < Size.PASSWORD_MIN_LENGTH.getSize() ||  user.getUsername().length() < Size.USERNAME_MIN_LENGTH.getSize()) {
            throw new BadRequestException("The username/password is too short");
        } else {
            users.create(user);
        }
    }

    @Override
    public void loginUser(String username, String password) throws SQLException {
        checkExistence(username, password);
    }

    @Override
    public void create(User entity) throws SQLException {
        if (!users.create(entity)) {
            throw new BadRequestException("An error while creating user occurred");
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        return users.getAll();
    }

    @Override
    public User findById(int id) throws SQLException {
        checkExistence(id);
        return users.findById(id);
    }

    @Override
    public void edit(int id, User entity) throws SQLException {
        checkExistence(id);
        if (!users.edit(id, entity)
                || entity.getPassword().length() < Size.PASSWORD_MIN_LENGTH.getSize() || entity.getUsername().length() < Size.USERNAME_MIN_LENGTH.getSize()) {
            throw new BadRequestException("Invalid user data");
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        checkExistence(id);
        users.delete(id);
    }

    private void checkExistence(int id) throws SQLException {
        if (users.findById(id) == null) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND.getErrorMessage());
        }
    }

    private void checkExistence(String userName) throws SQLException {
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName)) {
                throw new BadRequestException("User already exists");
            }
        }
    }

    private void checkExistence(String userName, String password) throws SQLException {
        List<User> userList = users.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                return;
            }
        }
        throw new BadRequestException("Username or password is incorrect");
    }
}
