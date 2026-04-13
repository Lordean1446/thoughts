package com.lordean1446.thoughts.repository;

import com.lordean1446.thoughts.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
