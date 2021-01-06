package com.cursor.model;

import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;

public class Ticket {
    private int id;
    private static int idGenerator = 0;
    private String name;
    private String description;
    private User assignee;
    private User reporter;
    private Status status;
    private Priority priority;
    private int timeSpent;
    private int timeEstimated;

    public Ticket() {
       this.id = ++idGenerator;
    }

    public Ticket(String name,
                  String description,
                  User assignee,
                  User reporter,
                  Status status,
                  Priority priority,
                  int timeSpent,
                  int timeEstimated) {
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.timeSpent = timeSpent;
        this.timeEstimated = timeEstimated;
        this.id = ++idGenerator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getTimeEstimated() {
        return timeEstimated;
    }

    public void setTimeEstimated(int timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", assignee=" + assignee +
                ", reporter=" + reporter +
                ", status=" + status +
                ", priority=" + priority +
                ", timeSpent=" + timeSpent +
                ", timeEstimated=" + timeEstimated +
                '}';
    }
}
