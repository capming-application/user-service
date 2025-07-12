package com.camping.userservice.controller;

import com.camping.userservice.dto.TokenDto;
import com.camping.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/token")
    public ResponseEntity<TokenDto> getToken(OAuth2AuthenticationToken token, Model model) {
        return ResponseEntity.ok().body(authService.getToken(token, model));
    }

    @GetMapping("/login")
    public String login() {
        return "custom_login";
    }
}
