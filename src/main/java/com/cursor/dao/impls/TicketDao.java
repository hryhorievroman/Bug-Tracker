package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.Ticket;
import com.cursor.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TicketDao implements CRUD<Ticket> {
    private static TicketDao instance;
    private final Map<Integer, Ticket> tickets = new HashMap<>();

    private TicketDao() {
    }

    public static TicketDao getInstance() {
        if (instance == null) {
            instance = new TicketDao();
        }
        return instance;
    }

    @Override
    public boolean create(Ticket entity) {
        return tickets.put(entity.getId(), entity) == null;
    }

    @Override
    public List<Ticket> getAll() {
        return new ArrayList<>(tickets.values());
    }

    @Override
    public Ticket findById(int id) {
        return tickets.get(id);
    }

    @Override
    public boolean edit(int id, Ticket entity) {
        return tickets.replace(id, entity) != null;
    }

    @Override
    public boolean delete(int id) {
        return tickets.remove(id) != null;
    }
}