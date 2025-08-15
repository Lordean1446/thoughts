package com.thoughts.service;
import com.thoughts.model.entity.Thought;
import com.thoughts.model.entity.User;
import com.thoughts.repository.ThoughtRepository;
import com.thoughts.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ThoughtService {

    private static final Logger logger = LoggerFactory.getLogger(ThoughtService.class);

    private final ThoughtRepository thoughtRepository;
    private final UserRepository userRepository;

    // Removido @Autowired do construtor, mantendo a injeção via construtor
    public ThoughtService(ThoughtRepository thoughtRepository, UserRepository userRepository) {
        this.thoughtRepository = thoughtRepository;
        this.userRepository = userRepository;
    }

    // Método para criar um novo pensamento
    public Thought createThought(Thought thought, Long userId) {
        logger.info("Criando novo pensamento para usuário: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        thought.setUser(user);
        Thought savedThought = thoughtRepository.save(thought);
        logger.info("Pensamento criado com sucesso: {}", savedThought.getId());
        return savedThought;
    }

    // Método para encontrar um pensamento pelo ID
    @Transactional(readOnly = true)
    public Optional<Thought> findThoughtById(Long id) {
        logger.info("Buscando pensamento por ID: {}", id);
        return thoughtRepository.findById(id);
    }

    // Método para listar todos os pensamentos (ordenados por mais recentes)
    @Transactional(readOnly = true)
    public List<Thought> findAllThoughts() {
        logger.info("Listando todos os pensamentos");
        return thoughtRepository.findAllByOrderByCreatedAtDesc();
    }

    // Método para listar pensamentos de um usuário específico
    @Transactional(readOnly = true)
    public List<Thought> findThoughtsByUserId(Long userId) {
        logger.info("Listando pensamentos do usuário: {}", userId);
        return thoughtRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    // Método para atualizar um pensamento
    public Thought updateThought(Long id, Thought thoughtDetails) {
        logger.info("Atualizando pensamento: {}", id);
        Thought thought = thoughtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thought not found with id: " + id));
        thought.setContent(thoughtDetails.getContent());
        Thought updatedThought = thoughtRepository.save(thought);
        logger.info("Pensamento atualizado com sucesso: {}", updatedThought.getId());
        return updatedThought;
    }

    // Método para deletar um pensamento
    public void deleteThought(Long id) {
        logger.info("Deletando pensamento: {}", id);
        if (!thoughtRepository.existsById(id)) {
            logger.warn("Pensamento não encontrado para deleção: {}", id);
            throw new RuntimeException("Thought not found with id: " + id);
        }
        thoughtRepository.deleteById(id);
        logger.info("Pensamento deletado com sucesso: {}", id);
    }
}