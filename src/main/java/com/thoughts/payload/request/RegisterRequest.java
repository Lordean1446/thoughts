package com.thoughts.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "O username não pode ser vazio")
    @Size(min = 3, max = 20, message = "O username deve ter entre 3 e 20 caracteres")
    private String username;

    @NotBlank(message = "O email não pode ser vazio")
    @Size(max = 50, message = "O email deve ter no máximo 50 caracteres")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 6, max = 40, message = "A senha deve ter entre 6 e 40 caracteres")
    private String password;

    // Construtor padrão
    public RegisterRequest() {
    }

    // Construtor com todos os campos
    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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