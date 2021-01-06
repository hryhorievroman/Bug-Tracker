package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoDb implements CRUD<User> {
    private static final String userName = "roman";
    private static final String userPassword = "root";
    private static final String url = "jdbc:mysql://localhost:3306/bugtracker?serverTimezone=UTC";
    private static UserDaoDb instance;
    private final Map<Integer, User> users = new HashMap<>();

    private UserDaoDb() {
    }

    public static UserDaoDb getInstance() {
        if (instance == null) {
            instance = new UserDaoDb();
        }
        return instance;
    }

    @Override
    public boolean create(User entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            String input = "insert into users(name, password) values ('" + entity.getUsername() + "', '" + entity.getPassword() + "')";
            statement.executeUpdate(input);
            return true;
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            String input = "select id, name, password from users";
            resultSet = statement.executeQuery(input);
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String uName = resultSet.getString(2);
                String uPassword = resultSet.getString(3);
                User user = new User(uName, uPassword);
                users.put(Id, user);
            }
        }
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(int id) {
        return users.get(id);
    }

    @Override
    public boolean edit(int id, User entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String input = "update users set name = '" + entity.getUsername() + "', password = '" + entity.getPassword() + "' where id = '" + id + "'";
            statement.executeUpdate(input);
            return true;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            String input = "delete from users where id = '" + id + "'";
            statement.executeUpdate(input);
        }
        return true;
    }
}
