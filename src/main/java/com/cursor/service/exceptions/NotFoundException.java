package com.cursor.service.exceptions;

public class NotFoundException extends BaseException {

    private static final String USER_NOT_FOUND = "[...The object with such ID wasn't found. Please enter ID again...]";

    public NotFoundException() {
        super(USER_NOT_FOUND, ErrorCode.NOT_FOUND);
    }
}
