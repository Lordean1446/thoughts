package com.thoughts.security.jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}") // Injeta a chave secreta do JWT
    private String jwtSecret;

    @Value("${jwt.expiration}") // Injeta o tempo de expiração
    private int jwtExpirationInMs;

    // Gera a chave secreta a partir da string jwtSecret
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Gera um token JWT
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // O subject (assunto) do token será o email/username do usuário
                .setIssuedAt(new Date()) // Data de emissão
                .setExpiration(expiryDate) // Data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Assina o token com a chave e algoritmo
                .compact(); // Constrói o token e o compacta em uma string
    }

    // Obtém o nome de usuário (subject) de um token JWT
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Valida um token JWT
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException ex) {
            // Token inválido (assinatura inválida, formato malformado)
            System.out.println("JWT inválido: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            // Token expirado
            System.out.println("JWT expirado: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            // Token não suportado
            System.out.println("JWT não suportado: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            // Argumento ilegal (string JWT vazia, nula ou apenas espaços)
            System.out.println("JWT claims string vazia: " + ex.getMessage());
        }
        return false;
    }
}