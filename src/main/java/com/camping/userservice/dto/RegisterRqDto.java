package com.camping.userservice.dto;

public record RegisterRqDto(
        String email,
        String username,
        String password
) {}
