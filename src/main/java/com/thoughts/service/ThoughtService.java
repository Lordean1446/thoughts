package com.thoughts.service;
import com.thoughts.model.entity.Thought;
import com.thoughts.model.entity.User;
import com.thoughts.repository.ThoughtRepository;
import com.thoughts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ThoughtService {

    private final ThoughtRepository thoughtRepository;
    private final UserRepository userRepository;

    @Autowired
    public ThoughtService(ThoughtRepository thoughtRepository, UserRepository userRepository) {
        this.thoughtRepository = thoughtRepository;
        this.userRepository = userRepository;
    }

    // Método para criar um novo pensamento
    public Thought createThought(Thought thought, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        thought.setUser(user);
        return thoughtRepository.save(thought);
    }

    // Método para encontrar um pensamento pelo ID
    @Transactional(readOnly = true)
    public Optional<Thought> findThoughtById(Long id) {
        return thoughtRepository.findById(id);
    }

    // Método para listar todos os pensamentos (ordenados por mais recentes)
    @Transactional(readOnly = true)
    public List<Thought> findAllThoughts() {
        return thoughtRepository.findAllByOrderByCreatedAtDesc();
    }

    // Método para listar pensamentos de um usuário específico
    @Transactional(readOnly = true)
    public List<Thought> findThoughtsByUserId(Long userId) {
        return thoughtRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    // Método para atualizar um pensamento
    public Thought updateThought(Long id, Thought thoughtDetails) {
        Thought thought = thoughtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thought not found with id: " + id));

        thought.setContent(thoughtDetails.getContent());
        return thoughtRepository.save(thought);
    }

    // Método para deletar um pensamento
    public void deleteThought(Long id) {
        if (!thoughtRepository.existsById(id)) {
            throw new RuntimeException("Thought not found with id: " + id);
        }
        thoughtRepository.deleteById(id);
    }
}