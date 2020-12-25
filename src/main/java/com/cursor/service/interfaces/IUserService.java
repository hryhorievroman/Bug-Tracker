package com.cursor.service.interfaces;

import com.cursor.model.User;
import com.cursor.service.exceptions.BadRequestException;

public interface IUserService extends IService<User> {

    void registerUser(User user);

    void loginUser(String username, String password);
}
