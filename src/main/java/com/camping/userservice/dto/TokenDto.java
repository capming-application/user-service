package com.camping.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TokenDto(
        String accessToken,
        String refreshToken,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime accessTokenExpiredAt) {
}
