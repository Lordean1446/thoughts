package com.thoughts.controller.auth;

import com.thoughts.model.entity.User;
import com.thoughts.payload.request.LoginRequest;
import com.thoughts.payload.request.RegisterRequest;
import com.thoughts.payload.response.JwtResponse;
import com.thoughts.repository.UserRepository;
import com.thoughts.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager; // Gerencia a autenticação de usuários

    @Autowired
    UserRepository userRepository; // Para salvar novos usuários

    @Autowired
    PasswordEncoder passwordEncoder; // Para codificar senhas

    @Autowired
    JwtTokenProvider tokenProvider; // Para gerar JWTs

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), // Usamos o email como username para login
                        loginRequest.getPassword()
                )
        );

        // Define a autenticação no contexto de segurança do Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Gera o JWT
        String jwt = tokenProvider.generateToken(authentication);

        // Retorna o JWT na resposta
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // Verifica se o email já está em uso
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("Erro: Email já está em uso!", HttpStatus.BAD_REQUEST);
        }
        // Verifica se o username já está em uso
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>("Erro: Username já está em uso!", HttpStatus.BAD_REQUEST);
        }

        // Cria uma nova conta de usuário
        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()) // Codifica a senha!
        );

        userRepository.save(user);

        return new ResponseEntity<>("Usuário registrado com sucesso!", HttpStatus.CREATED);
    }
}