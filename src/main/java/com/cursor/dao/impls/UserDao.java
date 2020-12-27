package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.User;

import java.util.*;

public final class UserDao implements CRUD<User> {
    private static UserDao instance;
    private final Map<Integer, User> users = new HashMap<>();

    private UserDao() {
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public boolean create(User entity) {
        List<User> usersList = getAll();
        if (users.size() > 0) {
            User user = usersList.stream().max(Comparator.comparing(User::getId)).get();
            entity.setId(user.getId() + 1);
        } else {
            entity.setId(1);
        }
        return users.put(entity.getId(), entity) != null;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
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
