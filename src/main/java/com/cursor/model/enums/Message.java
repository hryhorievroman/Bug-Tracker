package com.cursor.model.enums;

public enum Message {
    TYPE_TO_EXIT("(for choosing another action enter \"exit\")"),
    USERNAME_PASSWORD_LENGTH("[...Username must contain at least 3 symbols. Password must contain at least 8 symbols...]");

    private final String messsage;

    Message(String messsage) {
        this.messsage = messsage;
    }

    public String getMessage() {
        return messsage;
    }
}
