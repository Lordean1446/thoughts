package com.lordean1446.thoughts.config;

import com.lordean1446.thoughts.domain.Post;
import com.lordean1446.thoughts.domain.User;
import com.lordean1446.thoughts.dto.AuthorDTO;
import com.lordean1446.thoughts.dto.ReactionDTO;
import com.lordean1446.thoughts.repository.PostRepository;
import com.lordean1446.thoughts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("11/03/2026"),"Mal posso esperar para chegar sexta-feira e cair na gandaia rs", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("12/03/2026"),"Tem dia que é noite... complicado...", new AuthorDTO(maria));
        Post post3 = new Post(null, sdf.parse("13/03/2026"),"Aqui na gandaia da Maria não tem internet... rs", new AuthorDTO(alex));

        ReactionDTO r1  = new ReactionDTO("like", sdf.parse("11/03/2026"), new AuthorDTO(alex));
        ReactionDTO r2  = new ReactionDTO("cry", sdf.parse("12/03/2026"), new AuthorDTO(bob));
        ReactionDTO r3  = new ReactionDTO("laugh", sdf.parse("13/03/2026"), new AuthorDTO(bob));

        post1.getReactions().add(r1);
        post2.getReactions().add(r2);
        post3.getReactions().add(r3);

        postRepository.saveAll(Arrays.asList(post1, post2, post3));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        alex.getPosts().add(post3);
        userRepository.saveAll(Arrays.asList(maria, alex));
    }
}