package com.cursor.service.exceptions;

public class BaseException extends Exception {
    private String errorMessage;
    private ErrorCode errorCode;

    public BaseException(String errorMessage, ErrorCode errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
