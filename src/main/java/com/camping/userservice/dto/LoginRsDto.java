package com.camping.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record LoginRsDto(
        String email,
        String username,
        String accessToken,
        String refreshToken,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        Date accessTokenExpiredAt
) {}
