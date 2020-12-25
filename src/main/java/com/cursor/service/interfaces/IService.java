package com.cursor.service.interfaces;

import com.cursor.service.exceptions.*;

import java.util.List;

public interface IService<T> {
    void create(T entity);

    List<T> getAll();

    T findById(int id);

    void edit(int id, T entity);

    void delete(int id);
}
