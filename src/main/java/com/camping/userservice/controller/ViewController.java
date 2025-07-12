package com.camping.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZoneId;

@Controller
public class ViewController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model, Authentication authentication) {
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));

        String registrationId = token.getAuthorizedClientRegistrationId();
        String principalName = token.getName();

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(registrationId, principalName);
        if (authorizedClient != null) {
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            if (accessToken != null) {
                System.out.println(accessToken.getTokenValue());
                System.out.println(accessToken.getScopes());
                System.out.println(accessToken.getTokenType());
                System.out.println(accessToken.getExpiresAt().atZone(ZoneId.of("Asia/Taipei")));
                System.out.println(accessToken.getClass());
                System.out.println(accessToken.getScopes());
                System.out.println(authorizedClient.getRefreshToken().getTokenValue());

            }
        }

        return "user-profile";
    }

    @GetMapping("/login")
    public String login() {
        return "custom_login";
    }
}
