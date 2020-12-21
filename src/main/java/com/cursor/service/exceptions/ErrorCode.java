package com.cursor.service.exceptions;

public enum ErrorCode {
    BAD_REQUEST(400),
    NOT_FOUND(404);

    private int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
