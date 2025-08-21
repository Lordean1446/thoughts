package com.thoughts.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("Usuário não encontrado."),
    THOUGHT_NOT_FOUND("Pensamento não encontrado."),
    DUPLICATE_USER("Usuário já cadastrado."),
    DUPLICATE_EMAIL("E-mail já cadastrado.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
