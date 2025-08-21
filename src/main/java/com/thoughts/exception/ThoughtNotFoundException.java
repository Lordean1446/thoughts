package com.thoughts.exception;

public class ThoughtNotFoundException extends RuntimeException {
    public ThoughtNotFoundException() {
        super(ErrorMessage.THOUGHT_NOT_FOUND.getMessage());
    }
}

