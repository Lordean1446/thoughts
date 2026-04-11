package com.lordean1446.thoughts.services;

import com.lordean1446.thoughts.domain.User;
import com.lordean1446.thoughts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        return repo.findAll();
    }
}