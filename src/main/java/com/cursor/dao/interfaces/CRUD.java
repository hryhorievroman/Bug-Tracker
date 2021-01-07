package com.cursor.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    boolean create(T entity) throws SQLException;

    List<T> getAll()throws SQLException;

    T findById(int id)throws SQLException;

    boolean edit(int id, T entity)throws SQLException;

    boolean delete(int id)throws SQLException;
}
