package com.cursor.service.interfaces;

import com.cursor.model.Ticket;
import com.cursor.model.User;
import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;

import java.util.List;

public interface Dashboard {

    int getTotalTime(User user);

    int getSpentTime(User user);

    Ticket mostTimeExpensiveTicket();

    List<Ticket> getTicketsByUser(User user);

    String getSystemStatistics();

    String getUserStatistics(User user);

    List<Ticket> getTicketsByStatus(Status status);

    List<Ticket> getTicketsByPriority(Priority priority);

}