package com.cursor.view;

import com.cursor.model.User;
import com.cursor.service.UserServiceImpl;
import com.cursor.service.exceptions.BadRequestException;
import com.cursor.service.exceptions.NotFoundException;
import java.util.Scanner;

public class LoginPage {
    public static final UserServiceImpl userService = new UserServiceImpl();
    public static final String USER_NOT_FOUND = "[...The user with such ID wasn't found. Please enter ID again...]";
    private final Actions actions = new Actions();
    private final Scanner scanner = new Scanner(System.in);

    public void showMainMenu() {
        boolean isActive = true;

        while (isActive) {
            showUnregisteredMenu();
            int menu = getNum();
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
                    System.out.println("Wrong number, please enter number 0-2");
                    showMainMenu();
                }
            }
        }
        System.out.println("Bug tracker was closed ");
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
        boolean isActive = true;
        while (isActive) {
            showRegisteredMenu();
            int menu1 = getNum();
            switch (menu1) {
                case 1 -> {
                    System.out.println("Lists of users: ");
                    userService.getAll().forEach(user -> System.out.println(user.toString()));
                }
                case 2 -> {
                    System.out.println("For search please enter User's ID: ");
                    User user = findUser();
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
                }
                case 5 -> actions.showActionsMenu();
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

    public static int getNum() {
        int number = -1;
        boolean wrongInfo = true;
        while (wrongInfo) {
            try {
                Scanner newScan = new Scanner(System.in);
                String line = newScan.nextLine();
                number = Integer.parseInt(line);
                wrongInfo = false;
            }
            catch (NumberFormatException exception) {
                System.out.println("Only digits are acceptable. Please enter some number");
            }
        }
        return number;
    }

    private void registerUser() {
        boolean wrongInfo = true;
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
        boolean wrongInfo = true;
        while (wrongInfo) {
            try {
                userService.loginUser(inputUserName(), inputPassword());
                wrongInfo = false;
            } catch (BadRequestException exception) {
                System.out.println("[...An error while logging in occurred. Please input the username and password again...]");
            }
        }
    }

    public static User findUser() {
        boolean wrongInfo = true;
        User user = null;
        while (wrongInfo) {
            try {
                int usersID = getNum();
                user = userService.findById(usersID);
                wrongInfo = false;
            }
            catch (NotFoundException exception) {
                System.out.println(USER_NOT_FOUND);
            }
        }
        return user;
    }

    private void editUser() {
        boolean wrongInfo = true;
        User newUser = findUser();
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
                System.out.println(USER_NOT_FOUND);
            }
        }
    }

    private void deleteUser() {
        User newUser = findUser();
        int usersID = newUser.getId();
        boolean wrongInfo = true;
        while (wrongInfo) {
            try {
                userService.delete(usersID);
                wrongInfo = false;
            }
            catch (NotFoundException exception) {
                System.out.println(USER_NOT_FOUND);
            }
        }
        System.out.println("User with id " + usersID + " was deleted\n");
    }
}