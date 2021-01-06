package com.cursor.view;

import java.sql.SQLException;

public class Menu {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        LoginPage loginPage = new LoginPage();
        loginPage.showMainMenu();
    }
}
