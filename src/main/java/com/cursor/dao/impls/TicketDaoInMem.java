package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TicketDaoInMem implements CRUD<Ticket> {
    private static TicketDaoInMem instance;
    private final Map<Integer, Ticket> tickets = new HashMap<>();
    private static int idGenerator = 0;

    private TicketDaoInMem() {
    }

    public static TicketDaoInMem getInstance() {
        if (instance == null) {
            instance = new TicketDaoInMem();
        }
        return instance;
    }

    @Override
    public boolean create(Ticket entity) {
        entity.setId(++idGenerator);
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
