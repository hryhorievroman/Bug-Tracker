package com.cursor.model;

import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;

public class Ticket {
    static int id;
    String name;
    String description;
    User assignee;
    User reporter;
    Status status;
    Priority priority;
    int timeSpent;
    int timeEstimated;

    public Ticket() {
        id++;
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
        id++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public static int getId() {
        return id;
    }
}
