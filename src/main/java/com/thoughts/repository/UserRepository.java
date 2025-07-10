package com.thoughts.repository;

import com.thoughts.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);


    // Para verificar se um username existe
    boolean existsByUsername(String username);

    // Para verificar se um email existe
    boolean existsByEmail(String email);
}