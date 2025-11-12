package com.resustainability.aakri.commons;

import com.resustainability.aakri.entity.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long ACCESS_EXPIRATION = 1000 * 60 * 60; // 1 hour
    private static final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days

    public String generateAccessToken(UserEntity user) {
        return generateToken(user, ACCESS_EXPIRATION);
    }

    public String generateRefreshToken(UserEntity user) {
        return generateToken(user, REFRESH_EXPIRATION);
    }

    public String generateToken(UserEntity user, long expiration) {
        final Map<String, Object> claims = Map.of(
                "user_id", user.getId(),
                "database", "kerala_db",
                "state", "Kerala"
        );
        return generateToken(claims, expiration);
    }

    public String generateToken(Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY)
                .compact();
    }
}