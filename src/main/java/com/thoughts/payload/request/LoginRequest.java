package com.thoughts.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "O email não pode ser vazio")
    private String email; // Usaremos email para login

    @NotBlank(message = "A senha não pode ser vazia")
    private String password;

    // Construtor padrão
    public LoginRequest() {
    }

    // Construtor com todos os campos
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}