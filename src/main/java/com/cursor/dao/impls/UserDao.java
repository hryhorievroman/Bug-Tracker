package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao implements CRUD<User> {
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public boolean create(User entity) {
        return users.put(User.getId(), entity) != null;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) users.values();
    }

    @Override
    public User findById(int id) {
        return users.get(id);
    }

    @Override
    public boolean edit(int id, User entity) {
        return users.replace(id, entity) != null;
    }

    @Override
    public boolean delete(int id) {
        return users.remove(id) != null;
    }
}
