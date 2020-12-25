package com.cursor.view.interfaces;

public interface Menu {
    void show();
    void back();
    default void logOut() {
        System.out.println("You are logged out of the Bug Tracker");
        System.exit(0);
    }
}
