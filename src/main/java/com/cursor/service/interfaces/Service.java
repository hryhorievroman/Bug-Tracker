package com.cursor.service.interfaces;

import com.cursor.service.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public interface Service<T> {
    void create(T entity) throws SQLException;

    List<T> getAll() throws SQLException;

    T findById(int id) throws SQLException;

    void edit(int id, T entity) throws SQLException;

    void delete(int id) throws SQLException;
}
