package com.thoughts.controller;

import com.thoughts.exception.DuplicateEmailException;
import com.thoughts.exception.DuplicateUserException;
import com.thoughts.exception.ThoughtNotFoundException;
import com.thoughts.exception.UserNotFoundException;
import com.thoughts.model.dto.ThoughtResponseDTO;
import com.thoughts.model.entity.Thought;
import com.thoughts.model.entity.User;
import com.thoughts.model.request.ThoughtRequestDTO;
import com.thoughts.repository.ThoughtRepository;
import com.thoughts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/thoughts")

public class ThoughtController {

    private final ThoughtRepository thoughtRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ThoughtController(ThoughtRepository thoughtRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.thoughtRepository = thoughtRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ThoughtResponseDTO>> getAllThoughts() {
        List<Thought> thoughts = thoughtRepository.findAll();
        List<ThoughtResponseDTO> thoughtDTOs = thoughts.stream()
                .map(thought -> modelMapper.map(thought, ThoughtResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(thoughtDTOs);
    }

    // Endpoint para criar um novo pensamento
    @PostMapping("/{userId}")
    public ResponseEntity<ThoughtResponseDTO> createThought(@PathVariable Long userId, @Valid @RequestBody ThoughtRequestDTO thoughtRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Thought thought = modelMapper.map(thoughtRequestDTO, Thought.class);
        thought.setUser(user);
        Thought savedThought = thoughtRepository.save(thought);
        ThoughtResponseDTO thoughtDTO = modelMapper.map(savedThought, ThoughtResponseDTO.class);
        return new ResponseEntity<>(thoughtDTO, HttpStatus.CREATED); // Retorna 201 Created
    }

    // Endpoint para obter um pensamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<ThoughtResponseDTO> getThoughtById(@PathVariable Long id) {
        Thought thought = thoughtRepository.findById(id)
                .orElseThrow(ThoughtNotFoundException::new);
        ThoughtResponseDTO thoughtDTO = modelMapper.map(thought, ThoughtResponseDTO.class);
        return ResponseEntity.ok(thoughtDTO);
    }

    // Endpoint para deletar um pensamento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThought(@PathVariable Long id) {
        if (thoughtRepository.existsById(id)) {
            thoughtRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Status 204 No Content para deleção bem-sucedida
        } else {
            throw new ThoughtNotFoundException();
        }
    }

    // Endpoint para atualizar um pensamento
    @PutMapping("/{id}")
    public ResponseEntity<ThoughtResponseDTO> updateThought(@PathVariable Long id, @Valid @RequestBody ThoughtRequestDTO thoughtDetailsDTO) {
        Thought existingThought = thoughtRepository.findById(id)
                .orElseThrow(ThoughtNotFoundException::new);
        modelMapper.map(thoughtDetailsDTO, existingThought);
        Thought updatedThought = thoughtRepository.save(existingThought);
        ThoughtResponseDTO thoughtDTO = modelMapper.map(updatedThought, ThoughtResponseDTO.class);
        return ResponseEntity.ok(thoughtDTO);
    }
}