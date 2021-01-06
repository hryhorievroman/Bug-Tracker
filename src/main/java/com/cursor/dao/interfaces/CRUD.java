package com.cursor.dao.interfaces;

import com.cursor.model.User;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    boolean create(T entity) throws ClassNotFoundException, SQLException;

    List<T> getAll() throws SQLException, ClassNotFoundException;

    T findById(int id) throws SQLException, ClassNotFoundException;

    boolean edit(int id, T entity) throws SQLException, ClassNotFoundException;

    boolean delete(int id) throws ClassNotFoundException, SQLException;
}
