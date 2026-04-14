package com.lordean1446.thoughts.resources;


import com.lordean1446.thoughts.domain.Post;
import com.lordean1446.thoughts.dto.ReactionDTO;
import com.lordean1446.thoughts.resources.util.URL;
import com.lordean1446.thoughts.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        List<Post> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/contentsearch")
    public ResponseEntity<List<Post>> findByBodyContaining(@RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        List<Post> list = service.findByBodyContaining(text);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        Post newPost = service.insert(post);
        return ResponseEntity.ok().body(newPost);
    }

    @PostMapping(value = "/{id}/reactions")
    public ResponseEntity<Post> addReaction(@PathVariable String id, @RequestBody ReactionDTO reaction) {
        Post post = service.addReaction(id, reaction);
        return ResponseEntity.ok().body(post);
    }

}