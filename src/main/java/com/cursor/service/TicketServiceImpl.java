package com.cursor.service;

import com.cursor.dao.impls.TicketDaoDb;
import com.cursor.dao.impls.TicketDaoInMem;
import com.cursor.model.Ticket;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.ErrorMessage;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.TicketService;

import java.sql.SQLException;
import java.util.List;

public class TicketServiceImpl implements TicketService {
    private final TicketDaoDb tickets = TicketDaoDb.getInstance();
//    private final TicketDaoInMem tickets = TicketDaoInMem.getInstance();

    @Override
    public void create(Ticket entity) throws SQLException {
        if (!tickets.create(entity)) {
            throw new BadRequestException("An error while creating a ticket occurred");
        }
    }

    @Override
    public List<Ticket> getAll() throws SQLException {
        return tickets.getAll();
    }

    @Override
    public Ticket findById(int id) throws SQLException {
        checkExistence(id);
        return tickets.findById(id);
    }

    @Override
    public void edit(int id, Ticket entity) throws SQLException {
        checkExistence(id);
        if (!tickets.edit(id, entity)) {
            throw new BadRequestException("Invalid ticket data");
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        checkExistence(id);
        tickets.delete(id);
    }

    private void checkExistence(int id) throws SQLException {
        if (tickets.findById(id) == null) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND.getErrorMessage());
        }
    }
}
