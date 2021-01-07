package com.cursor.service.interfaces;

import com.cursor.model.Ticket;
import com.cursor.model.User;
import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;

import java.sql.SQLException;
import java.util.List;

public interface Dashboard {

    int getTotalTime(User user) throws SQLException;

    int getSpentTime(User user) throws SQLException;

    Ticket mostTimeExpensiveTicket() throws SQLException;

    List<Ticket> getTicketsByUser(User user) throws SQLException;

    String getSystemStatistics() throws SQLException;

    String getUserStatistics(User user) throws SQLException;

    List<Ticket> getTicketsByStatus(Status status) throws SQLException;

    List<Ticket> getTicketsByPriority(Priority priority) throws SQLException;

}