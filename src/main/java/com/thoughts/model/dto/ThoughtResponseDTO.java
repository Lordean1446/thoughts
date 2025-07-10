package com.thoughts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThoughtResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserResponseDTO user;
}