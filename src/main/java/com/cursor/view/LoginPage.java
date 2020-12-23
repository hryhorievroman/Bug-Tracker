package com.cursor.view;

import com.cursor.view.interfaces.Menu;

import java.util.Scanner;

public class LoginPage implements Menu {
    String password;
    String name;
    Scanner scanner = new Scanner(System.in);

    void inputUserName() {
        System.out.println("Please enter name: ");
        this.name = scanner.next();
    }

    void inputPassword() {
        System.out.println("Please enter password: ");
        this.password = scanner.next();
        System.out.println("You are logged in");
    }

    @Override
    public void show() {
        System.out.println("1) Please enter '1' to Register");
        System.out.println("2) Please enter '2' to Login");
        System.out.println("3) Please enter '3' to exit");
        System.out.print("");
        int a = scanner.nextInt();

        switch (a) {
            case 1:
                a = 1;
                inputUserName();
                inputPassword();
                break;
            case 2:
                a = 2;
                inputUserName();
                inputPassword();
                break;
            case 3:
                a = 3;
                logOut();
                break;
            default:
                System.out.println("Error number");
                break;
        }
    }

    @Override
    public void logOut() {
    }

    @Override
    public void back() {
        System.out.println("You are logged out of the Bug Tracker");
        System.exit(0);
    }
}

