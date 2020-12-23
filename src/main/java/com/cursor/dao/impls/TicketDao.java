package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDao implements CRUD<Ticket> {
    private final Map<Integer, Ticket> tickets = new HashMap<>();

    @Override
    public boolean create(Ticket entity) {
        return tickets.put(Ticket.getId(), entity) != null;
    }

    @Override
    public List<Ticket> getAll() {
        return (List<Ticket>) tickets.values();
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
