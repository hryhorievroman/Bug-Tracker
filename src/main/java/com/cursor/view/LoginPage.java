package com.cursor.view;

import com.cursor.model.User;
import com.cursor.service.UserServiceImpl;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import java.util.Scanner;

public class LoginPage {
    public static final UserServiceImpl userService = new UserServiceImpl();
    private final Actions actions = new Actions();
    private final Scanner scanner = new Scanner(System.in);
    private boolean isActive;
    private boolean wrongInfo;

    public void showMainMenu() {
        isActive = true;

        while (isActive) {
            showUnregisteredMenu();
            int menu = Utils.getNum();
            switch (menu) {
                case 1 -> {
                    registerUser();
                    showUsersMenu();
                }
                case 2 -> {
                    loginUser();
                    showUsersMenu();
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
        System.out.println("Please enter login: ");
        return scanner.next();
    }

    public String inputPassword() {
        System.out.println("Please enter password(min 8 symbols): ");
        return scanner.next();
    }

    public void showUsersMenu() {
        isActive = true;
        while (isActive) {
            showRegisteredMenu();
            int action = Utils.getNum();
            switch (action) {
                case 1 -> {
                    System.out.println("Lists of users: ");
                    userService.getAll().forEach(user -> System.out.println(user.toString()));
                }
                case 2 -> {
                    System.out.println("For search please enter User's ID: ");
                    User user = Utils.findUser();
                    System.out.println(user + "\n");
                }
                case 3 -> {
                    System.out.println("Please enter User's ID to edit Name and Password: ");
                    editUser();
                    System.out.println("User's name and password was changed");
                }
                case 4 -> {
                    System.out.println("For delete User please enter User's ID: ");
                    deleteUser();
                    isActive = false;
                }
                case 5 -> actions.showActionsMenu();
                case 0 -> isActive = false;
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
        System.out.println("'0' - Back to main menu");
    }

    private void registerUser() {
        wrongInfo = true;
        while (wrongInfo) {
            try {
                userService.registerUser(new User(inputUserName(), inputPassword()));
                wrongInfo = false;
            } catch (BadRequestException exception) {
                System.out.println("[...Username should NOT be empty. Password must contain at least 8 characters...]");
            }
        }
    }

    private void loginUser() {
        wrongInfo = true;
        while (wrongInfo) {
            try {
                userService.loginUser(inputUserName(), inputPassword());
                wrongInfo = false;
            } catch (BadRequestException exception) {
                System.out.println("[...An error while logging in occurred. Please input the username and password again...]");
            }
        }
    }

    private void editUser() {
        wrongInfo = true;
        User newUser = Utils.findUser();
        int usersID = newUser.getId();
        while (wrongInfo) {
            try {
                newUser.setUsername(inputUserName());
                newUser.setPassword(inputPassword());
                userService.edit(usersID, newUser);
                wrongInfo = false;
            }
            catch (BadRequestException exception) {
                System.out.println("[...Username should NOT be empty. Password must contain at least 8 characters. Please set ID and new information again...]");
            }
            catch (NotFoundException exception) {
                System.out.println(exception.getErrorMessage());
            }
        }
    }

    private void deleteUser() {
        User newUser = Utils.findUser();
        int usersID = newUser.getId();
        wrongInfo = true;
        while (wrongInfo) {
            try {
                userService.delete(usersID);
                wrongInfo = false;
            }
            catch (NotFoundException exception) {
                System.out.println(exception.getErrorMessage());
            }
        }
        System.out.println("User with id " + usersID + " was deleted\n");
    }
}