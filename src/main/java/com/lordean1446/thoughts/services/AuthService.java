package com.lordean1446.thoughts.services;

import com.lordean1446.thoughts.domain.User;
import com.lordean1446.thoughts.dto.AuthorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    private User currentUser;

    public User getCurrentUser() {
        if (currentUser == null) {
            // Simulates Maria Brown as logged-in user
            currentUser = userService.findAll().getFirst();
        }
        return currentUser;
    }

    public AuthorDTO getCurrentAuthor() {
        User user = getCurrentUser();
        return new AuthorDTO(user);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
