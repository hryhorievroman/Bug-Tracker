package com.cursor.service.interfaces;

import com.cursor.service.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public interface Service<T> {
    void create(T entity) throws SQLException, ClassNotFoundException;

    List<T> getAll() throws SQLException, ClassNotFoundException;

    T findById(int id) throws SQLException, ClassNotFoundException;

    void edit(int id, T entity) throws SQLException, ClassNotFoundException;

    void delete(int id) throws SQLException, ClassNotFoundException;
}
