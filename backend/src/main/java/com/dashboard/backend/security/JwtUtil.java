package com.dashboard.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dashboard.backend.entity.User;

@Component
public class JwtUtil {

    private static final Key key = Keys.hmacShaKeyFor(
        "mysecretkeymysecretkeymysecretkey12".getBytes()
    );

    // ✅ Generate Token
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_" + user.getRole().name()); // ADMIN or USER

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .signWith(key)
                .compact();
    }

    // ✅ Extract Email
    public static String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    
    //extract role
    public static String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.get("role", String.class);
    }

    // ✅ Validate Token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}