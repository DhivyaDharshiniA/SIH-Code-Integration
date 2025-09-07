package com.example.springapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiration = 1000 * 60 * 60; // 1 hour

    public String generateToken(String abhaId, String role) {
        return Jwts.builder()
                .setSubject(abhaId)
                .addClaims(Map.of("role", role))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Extract role from token
    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }

    // ✅ Extract ABHA ID (subject) from token
    public String extractAbhaId(String token) {
        return validateToken(token).getSubject();
    }
}
