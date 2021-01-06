package com.cursor.service.interfaces;

import com.cursor.model.User;
import com.cursor.service.exceptions.BadRequestException;

import java.sql.SQLException;

public interface UserService extends Service<User> {

    void registerUser(User user) throws SQLException, ClassNotFoundException;

    void loginUser(String username, String password) throws SQLException, ClassNotFoundException;
}
