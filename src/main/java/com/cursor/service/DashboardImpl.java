package com.cursor.service;

import com.cursor.dao.impls.TicketDao;
import com.cursor.dao.impls.UserDao;
import com.cursor.model.Ticket;
import com.cursor.model.User;
import com.cursor.model.enums.Status;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.Dashboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardImpl implements Dashboard {
    private TicketDao tickets = TicketDao.getInstance();
    private UserDao users = UserDao.getInstance();

    @Override
    public int getTotalTime(User user) {
        int time = 0;
        for (Ticket ticket : tickets.getAll()) {
            if (ticket.getAssignee().equals(user)) {
                time += ticket.getTimeEstimated();
            }
        }
        return time;
    }

    @Override
    public int getSpentTime(User user) {
        int time = 0;
        for (Ticket ticket : tickets.getAll()) {
            if (ticket.getAssignee().equals(user)) {
                time += ticket.getTimeSpent();
            }
        }
        return time;
    }

    @Override
    public Ticket mostTimeExpensiveTicket() {
        return tickets.getAll()
                .stream()
                .max(Comparator
                        .comparing(Ticket::getTimeEstimated))
                .orElseThrow(() -> new NotFoundException("Most time-expensive ticket not found"));
    }

    @Override
    public List<Ticket> getTicketsByUser(User user) {
        List<Ticket> ticketsByUser = new ArrayList<>();
        for (Ticket ticket : tickets.getAll()) {
            if (ticket.getAssignee().equals(user)) {
                ticketsByUser.add(ticket);
            }
        }
        return ticketsByUser;
    }

    @Override
    public String getSystemStatistics() {
        int usersCount = users.getAll().size();
        int ticketCount = tickets.getAll().size();
        return "There are in System " + usersCount + " users and " + ticketCount + " tickets";
    }

    @Override
    public String getUserStatistics(User user) {
        List<Ticket> ticketsByUser = getTicketsByUser(user);
        var finishedTickets = getStatusStatistics(ticketsByUser, Status.APPROVED);
        var inProgressTickets = getStatusStatistics(ticketsByUser, Status.IN_PROGRESS);
        var ticketsToDo = getStatusStatistics(ticketsByUser, Status.TODO);
        List<Ticket> createTickets = tickets.getAll()
                .stream()
                .filter(ticket -> ticket.getReporter().equals(user))
                .collect(Collectors.toList());
        return "User: " + user.getUsername() +
                "creates " + createTickets.size() + " tickets, " +
                "works on  " + ticketsByUser.size() + " tickets. " +
                ticketsToDo + inProgressTickets + finishedTickets;

    }

    private String getStatusStatistics(List<Ticket> tickets, Status status) {
        return tickets
                .stream()
                .filter(ticket -> ticket.getStatus().equals(status))
                .collect(Collectors.teeing(
                        Collectors.counting(),
                        Collectors.toList(),
                        (number, sortTickets) -> status.toString() + ": " + number +
                                " tickets: " + sortTickets + "\n"));
    }


}