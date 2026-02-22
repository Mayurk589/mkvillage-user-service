package com.mkvillage.mkvillage_user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // ==============================
    // Get Signing Key
    // ==============================
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ==============================
    // Generate Token WITH ROLES
    // ==============================
    public String generateToken(String mobile, Set<String> roles) {

        return Jwts.builder()
                .setSubject(mobile)
                .claim("roles", roles)  // Stored internally as List
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ==============================
    // Extract Claims
    // ==============================
    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ==============================
    // Extract Mobile
    // ==============================
    public String extractMobile(String token) {
        return extractClaims(token).getSubject();
    }

    // ==============================
    // Extract Roles (FIXED)
    // ==============================
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {

        Object rolesObject = extractClaims(token).get("roles");

        if (rolesObject instanceof List<?>) {
            return (List<String>) rolesObject;
        }

        return List.of();
    }

    // ==============================
    // Validate Token
    // ==============================
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
