package com.lordean1446.thoughts.services;

import com.lordean1446.thoughts.domain.Post;
import com.lordean1446.thoughts.dto.AuthorDTO;
import com.lordean1446.thoughts.dto.ReactionDTO;
import com.lordean1446.thoughts.repository.PostRepository;
import com.lordean1446.thoughts.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    @Autowired
    private AuthService authService;

    public List<Post> findAll() {
        return repo.findAllByOrderByDateDesc();
    }

    public Post findById(String id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Post not found"));
    }

    public List<Post> findByBodyContaining(String text) {
        return repo.findByBodyContainingIgnoreCase(text);
    }

    public Post insert(Post post) {
        post.setId(null); // Ensures MongoDB generates a new ID
        post.setDate(new Date());
        post.setAuthor(authService.getCurrentAuthor());
        return repo.save(post);
    }

    public Post addReaction(String id, ReactionDTO reaction) {
        Post post = findById(id);
        AuthorDTO currentUser = authService.getCurrentAuthor();
        
        // Toggle reaction logic
        // Remove existing reaction if exists
        String previousReaction = post.getReactions().stream()
            .filter(r -> r.getAuthor().getId().equals(currentUser.getId()))
            .map(ReactionDTO::getReaction)
            .findFirst()
            .orElse(null);

        post.getReactions().removeIf(r -> r.getAuthor().getId().equals(currentUser.getId()));

        // Switch reaction if different or add new reaction
        if (previousReaction == null || !previousReaction.equals(reaction.getReaction())) {
            reaction.setDate(new Date());
            reaction.setAuthor(currentUser);
            post.getReactions().add(reaction);
        }
        
        return repo.save(post);
    }
}