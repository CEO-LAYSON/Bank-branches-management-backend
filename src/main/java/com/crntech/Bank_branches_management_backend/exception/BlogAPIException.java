package com.crntech.Bank_branches_management_backend.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
