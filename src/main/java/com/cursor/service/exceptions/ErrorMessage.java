package com.cursor.service.exceptions;

public enum ErrorMessage {
    NOT_FOUND("[...The object with such ID wasn't found. Please enter ID again...]");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
