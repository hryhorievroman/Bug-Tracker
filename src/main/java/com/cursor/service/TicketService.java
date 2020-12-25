package com.cursor.service;

import com.cursor.model.Ticket;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.dao.interfaces.CRUD;
import com.cursor.dao.impls.TicketDao;

import java.util.List;


public class TicketService implements com.cursor.service.interfaces.TicketService {

    private static TicketService ticketService;
    private final CRUD<Ticket> tickets = new TicketDao();

    private TicketService() { }

    public static TicketService getInstance() {
        if (ticketService == null) {
            ticketService = new TicketService();
        }
        return ticketService;
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
        ifExists(id);
        return tickets.findById(id);
    }

    @Override
    public void edit(int id, Ticket entity) {
        ifExists(id);
        if (!tickets.edit(id, entity)) {
            throw new BadRequestException("Invalid ticket data");
        }
    }

    @Override
    public void delete(int id) {
        ifExists(id);
        tickets.delete(id);
    }

    private void ifExists(int id) { // check if ticket exists and throws exception if not
        if (tickets.findById(id) == null) {
            throw new NotFoundException("The user was not found");
        }
    }
}
