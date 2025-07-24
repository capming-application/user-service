package com.camping.userservice.repository;

import com.camping.userservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
