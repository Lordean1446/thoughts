package com.thoughts.controller;

import com.thoughts.model.dto.ThoughtResponseDTO;
import com.thoughts.model.entity.Thought;
import com.thoughts.model.entity.User;
import com.thoughts.repository.ThoughtRepository;
import com.thoughts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ThoughtRepository thoughtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public ResponseEntity<ThoughtResponseDTO> createThought(@PathVariable Long userId, @Valid @RequestBody Thought thought) {
        // Encontrar o usuário pelo userId da URL
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        thought.setUser(user);
        Thought savedThought = thoughtRepository.save(thought);

        ThoughtResponseDTO thoughtDTO = modelMapper.map(savedThought, ThoughtResponseDTO.class);
        return new ResponseEntity<>(thoughtDTO, HttpStatus.CREATED);
    }


    // Endpoint para obter um pensamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<ThoughtResponseDTO> getThoughtById(@PathVariable Long id) {
        Optional<Thought> thoughtOptional = thoughtRepository.findById(id);
        if (thoughtOptional.isPresent()) {
            Thought thought = thoughtOptional.get();
            ThoughtResponseDTO thoughtDTO = modelMapper.map(thought, ThoughtResponseDTO.class);
            return ResponseEntity.ok(thoughtDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar um pensamento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThought(@PathVariable Long id) {
        if (thoughtRepository.existsById(id)) {
            thoughtRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Status 204 No Content para deleção bem-sucedida
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found se não existir
        }
    }

    // Endpoint para atualizar um pensamento
    @PutMapping("/{id}")
    public ResponseEntity<ThoughtResponseDTO> updateThought(@PathVariable Long id, @Valid @RequestBody Thought thoughtDetails) {
        Optional<Thought> thoughtOptional = thoughtRepository.findById(id);
        if (thoughtOptional.isPresent()) {
            Thought existingThought = thoughtOptional.get();
            existingThought.setContent(thoughtDetails.getContent());
            Thought updatedThought = thoughtRepository.save(existingThought);
            ThoughtResponseDTO thoughtDTO = modelMapper.map(updatedThought, ThoughtResponseDTO.class);
            return ResponseEntity.ok(thoughtDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}