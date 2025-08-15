package com.thoughts.service;

import com.thoughts.model.entity.User;
import com.thoughts.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    // Removido @Autowired do construtor, mantendo a injeção via construtor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        logger.info("Registrando novo usuário: {}", user.getUsername());
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("Username já existe: {}", user.getUsername());
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Email já existe: {}", user.getEmail());
            throw new RuntimeException("Email already exists!");
        }
        User savedUser = userRepository.save(user);
        logger.info("Usuário registrado com sucesso: {}", savedUser.getId());
        return savedUser;
    }

    // Método para encontrar um usuário pelo ID
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        logger.info("Buscando usuário por ID: {}", id);
        return userRepository.findById(id);
    }

    // Método para encontrar um usuário pelo username
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        logger.info("Buscando usuário por username: {}", username);
        return userRepository.findByUsername(username);
    }

    // Método para listar todos os usuários
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        logger.info("Listando todos os usuários");
        return userRepository.findAll();
    }

    // Método para atualizar um usuário
    public User updateUser(Long id, User userDetails) {
        logger.info("Atualizando usuário: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        User updatedUser = userRepository.save(user);
        logger.info("Usuário atualizado com sucesso: {}", updatedUser.getId());
        return updatedUser;
    }

    // Método para deletar um usuário
    public void deleteUser(Long id) {
        logger.info("Deletando usuário: {}", id);
        if (!userRepository.existsById(id)) {
            logger.warn("Usuário não encontrado para deleção: {}", id);
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        logger.info("Usuário deletado com sucesso: {}", id);
    }
}