package com.cursor.view;

import com.cursor.model.User;
import com.cursor.model.enums.Message;
import com.cursor.model.enums.Size;
import com.cursor.service.UserServiceImpl;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import com.cursor.service.interfaces.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginPage {
    public static final UserService userService = new UserServiceImpl();
    private final Actions actions = new Actions();
    private final Scanner scanner = new Scanner(System.in);
    private static final String TYPE_TO_EXIT = "(for choosing another action enter \"exit\")";
    private boolean isActive;
    private boolean wrongInfo;

    public void showMainMenu() throws SQLException {
        isActive = true;

        while (isActive) {
            showUnregisteredMenu();
            int menu = Utils.expectNumInput();
            switch (menu) {
                case 1 -> {
                    if (registerUser()) {
                        showUsersMenu();
                    }
                }
                case 2 -> {
                    if (loginUser()) {
                        showUsersMenu();
                    }
                }
                case 0 -> isActive = false;
                default -> {
                    System.out.println("[...Wrong number, please enter number from 0 to 2...]");
                    showMainMenu();
                }
            }
        }
    }

    public String inputUserName() {
        System.out.println("Please enter login " + TYPE_TO_EXIT + ":");
        return scanner.next();
    }

    public String inputPassword() {
        System.out.println("Please enter password(min " + Size.PASSWORD_MIN_LENGTH.getSize() + " symbols) " + TYPE_TO_EXIT + ":");
        return scanner.next();
    }

    public void showUsersMenu() throws SQLException {
        isActive = true;
        while (isActive) {
            showRegisteredMenu();
            int action = Utils.expectNumInput();
            switch (action) {
                case 1 -> {
                    System.out.println("Lists of users: ");
                    userService.getAll().forEach(user -> System.out.println(user.toString()));
                }
                case 2 -> {
                    System.out.println("For search please enter User's ID " + TYPE_TO_EXIT + ":");
                    User user = Utils.findUser();
                    if (user != null) {
                        System.out.println(user + "\n");
                    }
                }
                case 3 -> {
                    System.out.println("Please enter User's ID to edit Name and Password " + TYPE_TO_EXIT + ":");
                    editUser();
                }
                case 4 -> {
                    System.out.println("For delete User please enter User's ID " + TYPE_TO_EXIT + ":");
                    if (deleteUser()) {
                        isActive = false;
                    }
                }
                case 5 -> actions.showActionsMenu();
                case 0 -> showMainMenu();
                default -> {
                    System.out.println("[...Wrong number, please enter a number from 0 to 4...]");
                    showUsersMenu();
                }
            }
        }
    }

    private void showUnregisteredMenu() {
        System.out.println("============Main menu=============");
        System.out.println("Please choose next: ");
        System.out.println("'1' - Registration");
        System.out.println("'2' - Login");
        System.out.println("'0' - Exit");
    }

    private void showRegisteredMenu() {
        System.out.println("============User's menu=============");
        System.out.println("Please choose next: ");
        System.out.println("'1' - Show all registered users");
        System.out.println("'2' - Search user by ID");
        System.out.println("'3' - Edit users name and password by ID");
        System.out.println("'4' - Delete user");
        System.out.println("'5' - Ticket menu");
        System.out.println("'0' - Log out");
    }

    private boolean registerUser() throws SQLException {

        wrongInfo = false;

        while (!wrongInfo) {

            String userName = inputUserName();
            if (Utils.isExit(userName)) {
                return false;
            }

            String password = inputPassword();
            if (Utils.isExit(password)) {
                return false;
            }

            try {
                userService.registerUser(new User(userName, password));
                wrongInfo = !wrongInfo;
            } catch (BadRequestException exception) {
                System.out.println(Message.USERNAME_PASSWORD_LENGTH.getMessage());
            }
        }

        return true;
    }

    private boolean loginUser() throws SQLException {

        wrongInfo = false;

        while (!wrongInfo) {

            String userName = inputUserName();
            if (Utils.isExit(userName)) {
                return false;
            }

            String password = inputPassword();
            if (Utils.isExit(password)) {
                return false;
            }

            try {
                userService.loginUser(userName, password);
                wrongInfo = !wrongInfo;
            } catch (BadRequestException e) {
                System.out.println("[...An error while logging in occurred. Please input the username and password again...]");
            }
        }
        return true;
    }

    private void editUser() throws SQLException {
        User user = Utils.findUser();

        if (user == null) {
            return;
        }

        int usersID = user.getId();
        wrongInfo = false;

        while (!wrongInfo) {
            try {
                user.setUsername(inputUserName());
                user.setPassword(inputPassword());
                userService.edit(usersID, user);
                wrongInfo = !wrongInfo;
            }
            catch (BadRequestException exception) {
                System.out.println(Message.USERNAME_PASSWORD_LENGTH.getMessage());
            }
            catch (NotFoundException exception) {
                System.out.println(exception.getErrorMessage());
            }
        }

        System.out.println("User's name and password was changed");
    }

    private boolean deleteUser() throws SQLException {
        User user = Utils.findUser();

        if (user == null) {
            return false;
        }

        int usersID = user.getId();
        wrongInfo = false;

        while (!wrongInfo) {
            try {
                userService.delete(usersID);
                wrongInfo = !wrongInfo;
            }
            catch (NotFoundException exception) {
                System.out.println(exception.getErrorMessage());
            }
        }

        System.out.println("User with id " + usersID + " was deleted\n");
        return true;
    }
}