package com.thoughts.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super(ErrorMessage.DUPLICATE_USER.getMessage());
    }
}

