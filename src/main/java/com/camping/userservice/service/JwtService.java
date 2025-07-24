package com.camping.userservice.service;

import com.camping.userservice.entity.RefreshToken;
import com.camping.userservice.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET = "PpqJ1HufqS/bUjdWqpVdxvOq8Hu6Vnv3gk4q7ZqL1RU=";
    private final RefreshTokenRepository refreshTokenRepository;

    public String createAccessToken(String email) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
        return Jwts.builder()
                .setSubject(email)
                .claim("role", "USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public void saveRefreshToken(String email, String refreshToken) {
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setUserEmail(email);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setRevoked(false);
        refreshTokenEntity.setExpiresAt(LocalDateTime.now().plusDays(1));
        refreshTokenEntity.setLastUsedAt(LocalDateTime.now());
        refreshTokenEntity.setCreatedAt(LocalDateTime.now());
        refreshTokenRepository.save(refreshTokenEntity);
    }

    public String findEmailByRefreshToken(String refreshToken) {
        return null;
    }



}
