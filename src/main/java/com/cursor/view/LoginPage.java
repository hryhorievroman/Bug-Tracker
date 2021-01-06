package com.cursor.view;

import com.cursor.model.User;
import com.cursor.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginPage {
   Actions actions = new Actions();
    UserServiceImpl userService = new UserServiceImpl();
    Scanner scanner = new Scanner(System.in);

    public String inputUserName() {
        return scanner.next();
    }

    public String inputPassword() {
        return scanner.next();
    }


    public void showMainMenu() throws SQLException, ClassNotFoundException {
        boolean isActive = true;
        while (isActive) {
            showUnregisteredMenu();
            int menu = scanner.nextInt();
            switch (menu) {
                case 1 -> {
                    System.out.println("Please enter login and password(min 8 symbols)");
                    userService.registerUser(new User(inputUserName(), inputPassword()));
                    System.out.println(" ");
                    showUsersMenu();
                }
                case 2 -> {
                    System.out.println("Please enter login and password(min 8 symbols)");
                    userService.loginUser(inputUserName(), inputPassword());
                    System.out.println(" ");
                    showUsersMenu();
                }
                case 0 -> isActive = false;
                default -> {
                    System.out.println("Wrong number, please enter number 0-2");
                    showMainMenu();
                }
            }
        }
        System.out.println("Bug tracker was closed ");

    }

    public void showUsersMenu() throws SQLException, ClassNotFoundException {
        boolean isActive = true;
        while (isActive) {
            showRegisteredMenu();
            int menu1 = scanner.nextInt();
            switch (menu1) {
                case 1 -> {
                    System.out.println("Lists of users: ");
                    userService.getAll().forEach(user -> System.out.println(user.toString()));
                    System.out.println(" ");
                }
                case 2 -> {
                    System.out.println("For search please enter User's ID: ");
                    int usersID = scanner.nextInt();
                    userService.findById(usersID);
                    System.out.println(" ");
                }
                case 3 -> {
                    System.out.println("Please enter User's ID to edit Name and Password: ");
                    int usersID = scanner.nextInt();
                    userService.edit(usersID, new User(inputUserName(), inputPassword()));
                    System.out.println("User's name and password was changed");
                    System.out.println(" ");
                }
                case 4 -> {
                    System.out.println("For delete User please enter User's ID: ");
                    int usersID = scanner.nextInt();
                    userService.delete(usersID);
                    System.out.println("User with id " + usersID + " was deleted");
                    System.out.println(" ");
                }
                case 5 ->{ actions.showActionsMenu();
                    System.out.println("Actions menu");
                }
                case 0 -> isActive = false;
                default -> {
                    System.out.println("Wrong number, please enter a number 0-4: ");
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
}


