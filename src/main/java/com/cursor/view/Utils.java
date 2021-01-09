package com.cursor.view;

import com.cursor.model.User;
import com.cursor.service.UserServiceImpl;
import com.cursor.service.exceptions.NotFoundException;

import java.util.Scanner;

public class Utils {

    public static int expectNumInput() {
        int number = -1;
        boolean wrongInfo = true;
        while (wrongInfo) {
            try {
                Scanner newScan = new Scanner(System.in);
                String line = newScan.nextLine();
                if (!isExit(line)) {
                    number = Integer.parseInt(line);
                }
                wrongInfo = false;
            } catch (NumberFormatException exception) {
                System.out.println("Only digits are acceptable. Please enter some number");
            }
        }
        return number;
    }

    public static String expectStringInput() {
        Scanner newScan = new Scanner(System.in);
        boolean info = true;
        String data = "";
        while (info) {
            data = newScan.nextLine();
            if (!data.isBlank()) {
                info = false;
            } else {
                System.out.println("Please enter information");
            }

        }
        return data;
    }

    public static User findUser() {
        boolean wrongInfo = true;
        User user = null;
        while (wrongInfo) {
            try {
                int userID = expectNumInput();
                if (userID != -1) {
                    user = new UserServiceImpl().findById(userID);
                }
                wrongInfo = false;
            } catch (NotFoundException exception) {
                System.out.println(exception.getErrorMessage());
            }
        }
        return user;
    }

    public static boolean isExit(String line) {
        return line.matches("[Ee][Xx][Ii][Tt]");
    }
}
