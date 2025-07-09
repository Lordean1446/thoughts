package com.thoughts.repository;

import com.thoughts.model.entity.Thought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThoughtRepository extends JpaRepository<Thought, Long> {
    // Método para encontrar um pensamento pelo ID
    List<Thought> findByUser_IdOrderByCreatedAtDesc(Long userId);

    // Método para listar todos os pensamentos, ordenados por data de criação (mais recentes primeiro)
    List<Thought> findAllByOrderByCreatedAtDesc();
}