package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.Ticket;
import com.cursor.model.User;
import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;
import com.cursor.service.exceptions.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoDb implements CRUD<Ticket> {

    private static final String userName = "roman";
    private static final String userPassword = "root";
    private static final String url = "jdbc:mysql://localhost:3306/bugtracker?serverTimezone=UTC";

    private static TicketDaoDb instance;
    UserDaoDb userDaoDb = UserDaoDb.getInstance();

    private TicketDaoDb() {
    }

    public static TicketDaoDb getInstance() {
        if (instance == null) {
            instance = new TicketDaoDb();
        }
        return instance;
    }


    @Override
    public boolean create(Ticket entity) {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            String input = "insert into tickets(Name, description, assignee, reporter, status, priority, time_spent, time_estimated) values ('" + entity.getName() + "', '" + entity.getDescription() + "','" + entity.getAssignee().getId() + "','" +
                    entity.getReporter().getId() + "','" + entity.getStatus() + "','" + entity.getPriority() + "','" + entity.getTimeSpent() +
                    "','" + entity.getTimeEstimated() + "')";
            statement.executeUpdate(input);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Ticket> getAll() {
        ArrayList<Ticket> ticketsDb = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            String input = "select id, name, description, assignee, reporter, status, priority, time_spent, time_estimated from tickets";
            resultSet = statement.executeQuery(input);
            while (resultSet.next()) {
                int tId = resultSet.getInt(1);
                String tName = resultSet.getString(2);
                String tDescription = resultSet.getString(3);
                User tAssignee, tReporter;
                try {
                    tAssignee = userDaoDb.findById(resultSet.getInt(4));
                    tReporter = userDaoDb.findById(resultSet.getInt(5));
                } catch (NotFoundException e) {
                    tAssignee = new User();
                    tReporter = new User();
                }
                Status tStatus = inputTicketStatus(resultSet.getString(6));
                Priority tPriority = inputTicketPriority(resultSet.getString(7));
                int tTimeSpent = resultSet.getInt(8);
                int tTimeEstimated = resultSet.getInt(9);
                Ticket ticket = new Ticket(tName, tDescription, tAssignee, tReporter, tStatus, tPriority, tTimeSpent, tTimeEstimated);
                ticket.setId(tId);
                ticketsDb.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketsDb;
    }

    @Override
    public Ticket findById(int id) {
        ArrayList<Ticket> ticketsDb = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            String input = "select * from tickets where id = '" + id + "'";
            ResultSet resultSet = statement.executeQuery(input);
            while (resultSet.next()) {
                int tId = resultSet.getInt(1);
                String tName = resultSet.getString(2);
                String tDescription = resultSet.getString(3);
                User tAssignee, tReporter;
                try {
                    tAssignee = userDaoDb.findById(resultSet.getInt(4));
                    tReporter = userDaoDb.findById(resultSet.getInt(5));
                } catch (NotFoundException e) {
                    tAssignee = new User();
                    tReporter = new User();
                }
                Status tStatus = inputTicketStatus(resultSet.getString(6));
                Priority tPriority = inputTicketPriority(resultSet.getString(7));
                int tTimeSpent = resultSet.getInt(8);
                int tTimeEstimated = resultSet.getInt(9);
                Ticket ticket = new Ticket(tName, tDescription, tAssignee, tReporter, tStatus, tPriority, tTimeSpent, tTimeEstimated);
                ticket.setId(tId);
                ticketsDb.add(0, ticket);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (!ticketsDb.isEmpty()) {
            return ticketsDb.get(0);
        } else {
            throw new NotFoundException("Ticket doesn't found");
        }
    }

    @Override
    public boolean edit(int id, Ticket entity) {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String input = "update tickets set name = '" + entity.getName() + "', description = '" + entity.getDescription() + "', " +
                    "assignee = '" + entity.getAssignee().getId() + "', reporter = '" + entity.getReporter().getId() + "', status = '" + entity.getStatus() + "'," +
                    " priority = '" + entity.getPriority() + "', time_spent = '" + entity.getTimeSpent() + "', " +
                    "time_estimated = '" + entity.getTimeEstimated() + "' where id = '" + id + "'";
            statement.executeUpdate(input);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            String input = "delete from tickets where id = '" + id + "'";
            statement.executeUpdate(input);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public Priority inputTicketPriority(String menuStatus) {
        return switch (menuStatus) {
            case "TRIVIAL" -> Priority.TRIVIAL;
            case "MINOR" -> Priority.MINOR;
            case "MAJOR" -> Priority.MAJOR;
            case "CRITICAL" -> Priority.CRITICAL;
            case "BLOCKER" -> Priority.BLOCKER;
            default -> null;
        };
    }

    public Status inputTicketStatus(String menuStatus) {
        return switch (menuStatus) {
            case "TODO" -> Status.TODO;
            case "IN_PROGRESS" -> Status.IN_PROGRESS;
            case "IN_REVIEW" -> Status.IN_REVIEW;
            case "APPROVED" -> Status.APPROVED;
            case "DONE" -> Status.DONE;
            default -> null;
        };
    }
}
