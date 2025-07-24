package com.camping.userservice.controller;

import com.camping.userservice.dto.LoginRsDto;
import com.camping.userservice.dto.RegisterRqDto;
import com.camping.userservice.service.AuthService;
import com.camping.userservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @GetMapping("/oauth2-login/{provider}")
    public ResponseEntity<LoginRsDto> loginWithOAuth2(OAuth2AuthenticationToken authentication) {
        return ResponseEntity.ok().body(this.authService.loginWithOAuth2(authentication));
    }

    @GetMapping("/general-login")
    public ResponseEntity<LoginRsDto> loginWithGeneral(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        return ResponseEntity.ok().body(this.authService.loginWithGeneral());
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRqDto registerRqDto) {
        this.authService.register(registerRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    public Map<String, Object> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refresh_token");

        String email = jwtService.findEmailByRefreshToken(refreshToken);
        if (email == null) {
            throw new RuntimeException("Invalid refresh token!");
        }

        String newAccessToken = jwtService.createAccessToken(email);
        return Map.of("access_token", newAccessToken);
    }
}
