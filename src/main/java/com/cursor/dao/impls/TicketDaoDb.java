package com.cursor.dao.impls;

import com.cursor.dao.interfaces.CRUD;
import com.cursor.model.Ticket;
import com.cursor.model.User;
import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDaoDb implements CRUD<Ticket> {
    private static final String userName = "roman";
    private static final String userPassword = "root";
    private static final String url = "jdbc:mysql://localhost:3306/bugtracker?serverTimezone=UTC";
    private static TicketDaoDb instance;
    private final Map<Integer, Ticket> tickets = new HashMap<>();
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
    public boolean create(Ticket entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            String input = "insert into tickets(Name, description, assignee, reporter, status, priority, time_spent, time_estimated) values ('" + entity.getName() + "', '" + entity.getDescription() + "','" + entity.getAssignee() + "','" +
                    entity.getReporter() + "','" + entity.getStatus() + "','" + entity.getPriority() + "','" + entity.getTimeSpent() +
                    "','" + entity.getTimeEstimated() + "')";
            statement.executeUpdate(input);
            return true;
        }
    }

    @Override
    public List<Ticket> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            String input = "select id, name, description, assignee, reporter, status, priority, time_spent, time_estimated from tickets";
            resultSet = statement.executeQuery(input);
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String tName = resultSet.getString(2);
                String tDescription = resultSet.getString(3);
                User tAssignee = userDaoDb.findById(resultSet.getInt(4));
                User tReporter = userDaoDb.findById(resultSet.getInt(5));
                Status tStatus = inputTicketStatus(resultSet.getInt(6));
                Priority tPriority = inputTicketPriority(resultSet.getInt(7));
                int tTimeSpent = resultSet.getInt(8);
                int tTimeEstimated = resultSet.getInt(9);
                Ticket ticket = new Ticket(tName, tDescription, tAssignee, tReporter, tStatus, tPriority, tTimeSpent, tTimeEstimated);
                tickets.put(Id, ticket);
            }
        }
        return new ArrayList<>(tickets.values());
    }

    @Override
    public Ticket findById(int id) throws SQLException {
        return tickets.get(id);
    }

    @Override
    public boolean edit(int id, Ticket entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String input = "update tickets set name = '" + entity.getName() + "', description = '" + entity.getDescription() + "', " +
                    "assignee = '" + entity.getAssignee() + "', reporter = '" + entity.getReporter() + "',status = '" + entity.getStatus() + "'," +
                    " priority = '" + entity.getPriority() + "', time_spent = '" + entity.getTimeSpent() + "', " +
                    "time_estimated = '" + entity.getTimeEstimated() + "'";
            statement.executeUpdate(input);
            return true;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, userName, userPassword)) {
            Statement statement = connection.createStatement();
            String input = "delete from tickets where id = '" + id + "'";
            statement.executeUpdate(input);
        }
        return true;
    }

    public Priority inputTicketPriority(int menuStatus) {

        return switch (menuStatus) {
            case 1 -> Priority.TRIVIAL;
            case 2 -> Priority.MINOR;
            case 3 -> Priority.MAJOR;
            case 4 -> Priority.CRITICAL;
            case 5 -> Priority.BLOCKER;
            default -> null;
        };
    }

    public Status inputTicketStatus(int menuStatus) {
        return switch (menuStatus) {
            case 1 -> Status.TODO;
            case 2 -> Status.IN_PROGRESS;
            case 3 -> Status.IN_REVIEW;
            case 4 -> Status.APPROVED;
            case 5 -> Status.DONE;
            default -> null;
        };
    }
}




