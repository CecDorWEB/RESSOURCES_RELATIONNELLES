package com.RESSOURCES_RELATIONNELLES.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtils {

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_MS = 86400000; // 1 jour

    // ➕ On utilise l'email comme identifiant du token
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Le "subject" du token est maintenant l'email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Renvoie l'email
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
