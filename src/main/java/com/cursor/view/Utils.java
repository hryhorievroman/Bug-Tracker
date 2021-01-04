package com.cursor.view;

import com.cursor.model.User;
import com.cursor.service.UserServiceImpl;
import com.cursor.service.exceptions.NotFoundException;

import java.util.Scanner;

public class Utils {

    public static final UserServiceImpl userService = new UserServiceImpl();

    public Utils() { }

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
                System.out.println(exception.getErrorMessage());
            }
        }
        return user;
    }
}
