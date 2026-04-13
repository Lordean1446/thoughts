package com.lordean1446.thoughts.services;

import com.lordean1446.thoughts.domain.Post;
import com.lordean1446.thoughts.repository.PostRepository;
import com.lordean1446.thoughts.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    public Post findById(String id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Post not found"));
    }

    public List<Post> findByBodyContaining(String text) {
        return repo.findByBodyContainingIgnoreCase(text);
    }
}