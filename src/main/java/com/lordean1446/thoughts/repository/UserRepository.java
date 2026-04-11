package com.lordean1446.thoughts.repository;

import com.lordean1446.thoughts.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  MongoRepository<User, String> {
}