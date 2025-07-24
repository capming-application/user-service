package com.camping.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, columnDefinition = "text")
    private String token;

    @Column(name = "user_email", nullable = false, columnDefinition = "text")
    private String userEmail;

    @Column(nullable = false)
    private boolean revoked;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}