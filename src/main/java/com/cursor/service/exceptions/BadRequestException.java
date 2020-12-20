package com.cursor.service.exceptions;

public class BadRequestException extends BaseException {

    public BadRequestException(String errorMessage) {
        super(errorMessage, ErrorCode.BAD_REQUEST);
    }
}
