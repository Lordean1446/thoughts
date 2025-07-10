package com.thoughts.payload.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer"; // Tipo de token, padrão para JWTs

    // Construtor
    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }

    // Getters e Setters
    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}