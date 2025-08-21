package com.thoughts.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(ErrorMessage.DUPLICATE_EMAIL.getMessage());
    }
}

