package com.cursor.model.enums;

public enum Size {
    USERNAME_MIN_LENGTH(3),
    PASSWORD_MIN_LENGTH(8);

    private final int length;

    Size(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
