package com.cursor.service;

import com.cursor.model.Ticket;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.dao.interfaces.CRUD;
import com.cursor.dao.impls.TicketDao;

import java.util.List;

public class TicketServiceImpl implements com.cursor.service.interfaces.TicketService {

    private static TicketServiceImpl ticketServiceImpl;
    private final CRUD<Ticket> tickets = new TicketDao();
    private static final String USER_NOT_FOUND = "The user was not found";

    private TicketServiceImpl() { }

    public static TicketServiceImpl getInstance() {
        if (ticketServiceImpl == null) {
            ticketServiceImpl = new TicketServiceImpl();
        }
        return ticketServiceImpl;
    }

    @Override
    public void create(Ticket entity) {
        if (!tickets.create(entity)) {
            throw new BadRequestException("An error while creating a ticket occurred");
        }
    }

    @Override
    public List<Ticket> getAll() {
        return tickets.getAll();
    }

    @Override
    public Ticket findById(int id) {
        if (checkExistence(id)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return tickets.findById(id);
    }

    @Override
    public void edit(int id, Ticket entity) {
        if (checkExistence(id)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        if (!tickets.edit(id, entity)) {
            throw new BadRequestException("Invalid ticket data");
        }
    }

    @Override
    public void delete(int id) {
        if (checkExistence(id)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        tickets.delete(id);
    }

    private boolean checkExistence(int id) {
        return tickets.findById(id) == null;
    }
}
