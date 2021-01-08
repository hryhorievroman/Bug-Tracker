package com.cursor.view;

import com.cursor.model.Ticket;
import com.cursor.model.User;
import com.cursor.model.enums.Message;
import com.cursor.model.enums.Priority;
import com.cursor.model.enums.Status;
import com.cursor.service.DashboardImpl;
import com.cursor.service.TicketServiceImpl;
import com.cursor.service.UserServiceImpl;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.Dashboard;
import com.cursor.service.interfaces.TicketService;

import java.util.InputMismatchException;
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
            int menu = Utils.expectNumInput();
            switch (menu) {
                case 1 -> {
                    System.out.println("Please enter a ticket's information: " + Message.TYPE_TO_EXIT.getMessage() + ":");
                    Ticket ticket = new Ticket();
                    setTicket(ticket);
                    if (ticket.getTimeEstimated() != 0) {
                        ticketService.create(ticket);
                        System.out.println("The ticket was created");
                    }
                }
                case 2 -> {
                    System.out.println("List of tickets: ");
                    ticketService.getAll().forEach(ticket -> System.out.println(ticket.toString()));
                    System.out.println();
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
        boolean isFlowContinued = true;
        while (isFlowContinued) {
            try {
                System.out.println("Name: ");
                //String name = scanner.nextLine();
                String name = Utils.expectStringInput();
                if (name.isBlank()){
                    throw new BadRequestException("");
                }

                if (Utils.isExit(name)) {
                    return;
                }

                System.out.println("Description: ");
                String description = Utils.expectStringInput();
                if (description.isBlank()){
                    throw new BadRequestException("");
                }

                if (Utils.isExit(description)) {
                    return;
                }

                System.out.println("Assignee:");
                User assignee = Utils.findUser();

                if (assignee == null) {
                    return;
                }

                System.out.println("Reporter:");
                User reporter = Utils.findUser();

                if (reporter == null) {
                    return;
                }

                Status status = inputTicketStatus();

                Priority priority = inputTicketPriority();

                System.out.println("Time spent:");
                int timeSpent = Utils.expectNumInput();

                if (timeSpent == -1) {
                    return;
                }

                System.out.println("Time estimated:");
                int timeEstimated = Utils.expectNumInput();

                if (timeEstimated == -1) {
                    return;
                }

                if (timeSpent < 0 || timeEstimated < 0) {
                    throw new BadRequestException("A ticket's information is incorrect");
                } else {
                    ticket.setName(name);
                    ticket.setDescription(description);
                    ticket.setAssignee(assignee);
                    ticket.setReporter(reporter);
                    ticket.setStatus(status);
                    ticket.setPriority(priority);
                    ticket.setTimeSpent(timeSpent);
                    ticket.setTimeEstimated(timeEstimated);
                    isFlowContinued = false;
                }
            } catch (BadRequestException exception) {
                System.out.println("Please type a ticket's information correctly:" +
                        "\n\tName should NOT be empty" +
                        "\n\tDescription should NOT be empty" +
                        "\n\tIDs values should be correct" +
                        "\n\tEnter Status and Priority values as reserved word" +
                        "\n\tTime spent and Time estimated values should be larger than 0\n");
            }
        }
    }

    private Ticket findTicket() {
        boolean isFlowContinued = true;
        Ticket ticket = null;
        while (isFlowContinued) {
            try {
                int ticketID = Utils.expectNumInput();

                if (ticketID == -1) {
                    return null;
                }

                ticket = ticketService.findById(ticketID);
                isFlowContinued = false;
            } catch (NotFoundException exception) {
                System.out.println("[...The ticket with such ID wasn't found. Please enter ID again...]");
            }
        }
        return ticket;
    }

    private boolean deleteTicket() {
        boolean isFlowContinued = true;
        while (isFlowContinued) {
            try {
                Ticket ticket = findTicket();
                if (ticket != null) {
                    ticketService.delete(ticket.getId());
                    isFlowContinued = false;
                } else {
                    return false;
                }
            } catch (NotFoundException exception) {
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
                        int time = 0;
                        time = dashboard.getTotalTime(getUser());
                        System.out.println("Estimated time: " + time);
                    }
                    case 2 -> {
                        int time = 0;

                        time = dashboard.getSpentTime(getUser());

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
                    case 5 -> System.out.println(dashboard.getSystemStatistics());
                    case 6 -> System.out.println(dashboard.getUserStatistics(getUser()));
                    case 7 -> System.out.println(dashboard.getTicketsByStatus(inputTicketStatus()));
                    case 8 -> System.out.println(dashboard.getTicketsByPriority(inputTicketPriority()));
                    case 0 -> isActiveMenu = false;
                    default -> System.out.println("Wrong number, please enter a number 0-5:");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong input, please use numbers");
        }
    }

    private Status inputTicketStatus() {
        final String statusHintEnumeration = "todo/in_progress/in_review/approved/done";
        boolean isFlowContinued = true;
        String statusInput = "";
        while (isFlowContinued) {
            try {
                System.out.println("Status: " + statusHintEnumeration);
                statusInput = Utils.expectStringInput();
                if (statusInput.isBlank() ||
                        !statusInput.equals("todo") &&
                                !statusInput.equals("in_progress") &&
                                !statusInput.equals("in_review") &&
                                !statusInput.equals("approved") &&
                                !statusInput.equals("done")) {
                    throw new BadRequestException(" A ticket's status is incorrect.");
                } else {
                    isFlowContinued = false;
                }
            } catch (BadRequestException exception) {
                System.out.println("[...The status is incorrect. Please enter a new status in a valid form]");
            }
        }
        return switch (statusInput) {
            case "todo" -> Status.TODO;
            case "in_progress" -> Status.IN_PROGRESS;
            case "in_review" -> Status.IN_REVIEW;
            case "approved" -> Status.APPROVED;
            case "done" -> Status.DONE;
            default -> null;
        };
    }

    private Priority inputTicketPriority() {
        final String priorityHintEnumeration = "trivial/minor/major/critical/blocker";
        boolean isFlowContinued = true;
        String priorityInput = "";
        while (isFlowContinued) {
            try {
                System.out.println("Priority: " + priorityHintEnumeration);
                priorityInput = Utils.expectStringInput();
                if (priorityInput.isBlank() ||
                        !priorityInput.equals("trivial") &&
                                !priorityInput.equals("minor") &&
                                !priorityInput.equals("major") &&
                                !priorityInput.equals("critical") &&
                                !priorityInput.equals("blocker")) {
                    throw new BadRequestException("A ticket's priority is incorrect.");
                } else {
                    isFlowContinued = false;
                }
            } catch (BadRequestException exception) {
                System.out.println("[...A ticket's priority is incorrect. Please enter a new priority in a valid form ");
            }
        }
        return switch (priorityInput) {
            case "trivial" -> Priority.TRIVIAL;
            case "minor" -> Priority.MINOR;
            case "major" -> Priority.MAJOR;
            case "critical" -> Priority.CRITICAL;
            case "blocker" -> Priority.BLOCKER;
            default -> null;
        };
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
