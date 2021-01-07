package com.cursor.model.enums;

public enum Message {
    TYPE_TO_EXIT("(for choosing another action enter \"exit\")"),
    USERNAME_PASSWORD_LENGTH("[...Username must contain at least" + Size.USERNAME_MIN_LENGTH.getSize() + " symbols. Password must contain at least " + Size.PASSWORD_MIN_LENGTH.getSize() + " symbols...]");

    private final String messsage;

    Message(String messsage) {
        this.messsage = messsage;
    }

    public String getMessage() {
        return messsage;
    }
}
