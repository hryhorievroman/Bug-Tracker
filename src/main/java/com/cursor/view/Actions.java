package com.cursor.view;

import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.model.Ticket;
import com.cursor.model.User;
import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;
import com.cursor.service.DashboardImpl;
import com.cursor.service.TicketServiceImpl;
import com.cursor.service.interfaces.TicketService;
import com.cursor.model.enums.Message;
import com.cursor.service.UserServiceImpl;
import com.cursor.service.interfaces.Dashboard;

import java.util.Scanner;

public class Actions {
    private final TicketService ticketService = new TicketServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    UserServiceImpl userService = new UserServiceImpl();
    Dashboard dashboard = new DashboardImpl();

    public void showActionsMenu() {
        boolean isActive = true;
        while (isActive) {
            showTicketsMenu();
            int menu = Utils.getNum();
            switch (menu) {
                case 1 -> {
                    System.out.println("Please enter a ticket's information: " + Message.TYPE_TO_EXIT.getMessage() + ":");
                    Ticket ticket = new Ticket();
                    setTicket(ticket);
                    if (ticket != null) {
                        ticketService.create(ticket);
                        System.out.println("The ticket was created");
                    }
                }
                case 2 -> {
                    System.out.println("Lists of tickets: ");
                    ticketService.getAll().forEach(ticket -> System.out.println(ticket.toString()));
                    System.out.println("");
                }
                case 3 -> {
                    System.out.println("For search please enter ticket's ID: " + Message.TYPE_TO_EXIT.getMessage() + ":");
                    System.out.println(findTicket() + "\n");
                }
                case 4 -> {
                    System.out.println("Please enter Ticket's ID to edit: " + Message.TYPE_TO_EXIT.getMessage() + ":");
                    Ticket ticket = findTicket();
                    if (ticket != null) {
                        System.out.println("Ticket's information:\n" + ticket + "\nPlease enter the Ticket's new information");
                        setTicket(ticket);
                        ticketService.edit(ticket.getId(), ticket);
                        System.out.println("The ticket was changed");
                    }
                }
                case 5 -> {
                    System.out.println("For delete a ticket please enter ticket's ID: " + Message.TYPE_TO_EXIT.getMessage() + ":");
                    if (deleteTicket()) {
                        System.out.println("The ticket was removed");
                    }
                }
                case 6 -> getDashboard();
                case 0 -> isActive = false;
                default -> System.out.println("Wrong number, please enter a number 0-5:");
            }
        }
    }

    public void setTicket(Ticket ticket) {
        boolean wrongInfo = false;
        while (!wrongInfo) {
            try {
                System.out.println("Name: ");
                String name = scanner.nextLine();

                if (Utils.isExit(name)) {
                    return;
                }

                System.out.println("Description: ");
                String description = scanner.nextLine();

                if (Utils.isExit(description)) {
                    return;
                }

                System.out.println("Asignee:");
                User asignee = Utils.findUser();

                if (asignee == null) {
                    return;
                }

                System.out.println("Reporter:");
                User reporter = Utils.findUser();

                if (reporter == null) {
                    return;
                }

                System.out.println("Status: 1 - ToDo, 2 -InProgress, 3 – In Review, 4 – Approved, 5 - Done");
                Status status;
                int statusNum = Utils.getNum();

                if (statusNum == -1) {
                    return;
                }


                System.out.println("Priority: 1 - Trivial, 2 - Minor, 3 – Major, 4 – Critical, 5 - Blocker");
                Priority priority;
                int priorityNum = Utils.getNum();

                if (priorityNum == -1) {
                    return;
                }

                System.out.println("Time spent:");
                int timeSpent = Utils.getNum();

                if (timeSpent == -1) {
                    return;
                }

                System.out.println("Time estimated:");
                int timeEstimated = Utils.getNum();

                if (timeEstimated == -1) {
                    return;
                }

                if (name.isBlank() || description.isBlank()
                        || statusNum < 1|| statusNum > 5
                        || priorityNum < 1 || priorityNum > 5
                        || timeSpent < 0 || timeEstimated < 0) {
                    throw new BadRequestException("A ticket's information is incorrect");
                }
                else {
                    status = switch (statusNum) {
                        case 1 -> Status.TODO;
                        case 2 -> Status.IN_PROGRESS;
                        case 3 -> Status.IN_REVIEW;
                        case 4 -> Status.APPROVED;
                        case 5 -> Status.DONE;
                        default -> null;
                    };

                    priority = switch (priorityNum) {
                        case 1 -> Priority.TRIVIAL;
                        case 2 -> Priority.MINOR;
                        case 3 -> Priority.MAJOR;
                        case 4 -> Priority.CRITICAL;
                        case 5 -> Priority.BLOCKER;
                        default -> null;
                    };
                    ticket.setName(name);
                    ticket.setDescription(description);
                    ticket.setAssignee(asignee);
                    ticket.setReporter(reporter);
                    ticket.setStatus(status);
                    ticket.setPriority(priority);
                    ticket.setTimeSpent(timeSpent);
                    ticket.setTimeEstimated(timeEstimated);
                    wrongInfo = !wrongInfo;
                }
            } catch (BadRequestException exception) {
                System.out.println("Please type a ticket's information correctly:" +
                        "\n\tDescription should NOT be empty" +
                        "\n\tIDs values should be correct" +
                        "\n\tEnter Status and Priority values as digits from 0 to 5" +
                        "\n\tTime spent and Time estimated values should be larger than 0\n");
            }
        }
        return;
    }

    private Ticket findTicket() {
        boolean wrongInfo = false;
        Ticket ticket = null;
        while (!wrongInfo) {
            try {
                int ticketID = Utils.getNum();

                if (ticketID == -1) {
                    return null;
                }

                ticket = ticketService.findById(ticketID);
                wrongInfo = !wrongInfo;
            } catch (NotFoundException exception) {
                System.out.println("[...The ticket with such ID wasn't found. Please enter ID again...]");
            }
        }
        return ticket;
    }

    private boolean deleteTicket() {
        boolean wrongInfo = false;
        while (!wrongInfo) {
            try {
                Ticket ticket = findTicket();
                if (ticket != null) {
                    ticketService.delete(ticket.getId());
                    wrongInfo = !wrongInfo;
                }
                else {
                    return false;
                }
            }
            catch (NotFoundException exception) {
                System.out.println("[...The ticket with such ID wasn't found. Please enter ID again...]");
            }
        }
        return true;
    }

    private void getDashboard() {
        try {
            boolean isActiveMenu = true;
            while (isActiveMenu) {
                showDashboardMenu();
                int menu = scanner.nextInt();
                switch (menu) {
                    case 1 -> {
                        int time = dashboard.getTotalTime(getUser());
                        System.out.println("Estimated time: " + time);
                    }
                    case 2 -> {
                        int time = dashboard.getSpentTime(getUser());
                        System.out.println("Spent time: " + time);
                    }
                    case 3 -> {
                        System.out.println("Most time expensive ticket: ");
                        System.out.println(dashboard.mostTimeExpensiveTicket());
                    }
                    case 4 -> {
                        System.out.println("Tickets: ");
                        System.out.println(dashboard.getTicketsByUser(getUser()));
                    }
                    case 5 -> {
                        System.out.println(dashboard.getSystemStatistics());
                    }
                    case 6 -> {
                        System.out.println(dashboard.getUserStatistics(getUser()));
                    }
                    case 7 -> {
                        System.out.println(dashboard.getTicketsByStatus(inputTicketStatus()));
                    }
                    case  8 -> {
                        System.out.println(dashboard.getTicketsByPriority(inputTicketPriority()));
                    }
                    case 0 -> isActiveMenu = false;
                    default -> System.out.println("Wrong number, please enter a number 0-5:");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong input, please use numbers");
        }
    }

    private User getUser() {
        System.out.println("Enter, please, User's ID");
        int UserID = scanner.nextInt();
        return userService.findById(UserID);
    }

    private void showDashboardMenu() {
        System.out.println("============Dashboard menu=============");
        System.out.println("Please choose next: ");
        System.out.println("'1' - Show estimated time");
        System.out.println("'2' - Show spent time");
        System.out.println("'3' - Find most time-expensive ticket");
        System.out.println("'4' - Show tickets for special user");
        System.out.println("'5' - Show System Statistics");
        System.out.println("'6' - Show User Statistics");
        System.out.println("'7' - Find tickets with special status");
        System.out.println("'8' - Find tickets with special priority");
        System.out.println("'0' - Back to Ticket's menu");
    }

    private void showTicketsMenu() {
        System.out.println("============Ticket menu=============");
        System.out.println("Please choose next: ");
        System.out.println("'1' - Create new ticket ");
        System.out.println("'2' - Show all tickets");
        System.out.println("'3' - Find ticket by ID");
        System.out.println("'4' - Edit ticket by ID ");
        System.out.println("'5' - Delete ticket by ID");
        System.out.println("'6' - Dashboard menu");
        System.out.println("'0' - Back to User's menu");
    }
}
