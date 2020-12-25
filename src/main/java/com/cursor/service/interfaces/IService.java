package com.cursor.service.interfaces;

import com.cursor.service.exceptions.*;

import java.util.List;

public interface IService<T> {
    void create(T entity) throws BadRequestException;

    List<T> getAll();

    T findById(int id) throws NotFoundException;

    void edit(int id, T entity) throws BadRequestException, NotFoundException;

    void delete(int id) throws NotFoundException;
}
