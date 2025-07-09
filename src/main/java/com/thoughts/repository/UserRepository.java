package com.thoughts.repository;
import com.thoughts.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Para encontrar um usuário pelo ID:
    User findByUsername(String username);

    // Para encontrar um usuário pelo email:
    User findByEmail(String email);

    // Para verificar se um username existe
    boolean existsByUsername(String username);

    // Para verificar se um email existe
    boolean existsByEmail(String email);
}