package com.cursor.service.exceptions;

public class NotFoundException extends BaseException {

    public NotFoundException(String errorMessage) {
        super(errorMessage, ErrorCode.NOT_FOUND);
    }
}
