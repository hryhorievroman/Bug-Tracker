package com.cursor.dao.interfaces;

import java.util.List;

public interface CRUD<T> {
    void create(T entity);

    List<T> getAll();

    T findById(int id);

    boolean delete(int id);
}
