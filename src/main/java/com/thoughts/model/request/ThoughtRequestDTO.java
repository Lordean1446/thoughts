package com.thoughts.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ThoughtRequestDTO {

    @NotBlank(message = "O conteúdo do pensamento não pode estar vazio.")
    @Size(min = 5, max = 500, message = "O pensamento deve ter entre 5 e 500 caracteres.")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
