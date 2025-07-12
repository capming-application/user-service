package com.camping.userservice.service;

import com.camping.userservice.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AuthService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public TokenDto getToken(OAuth2AuthenticationToken token, Model model) {
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));

        String registrationId = token.getAuthorizedClientRegistrationId();
        String principalName = token.getName();

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(registrationId, principalName);
        String accessTokenValue = null;
        String refreshTokenValue = null;
        LocalDateTime accessTokenExpiresAt = null;

        System.out.println("registrationId = " + registrationId);
        System.out.println("principalName = " + principalName);
        System.out.println("authorizedClient = " + authorizedClient);

        if (authorizedClient != null) {
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();

            if (accessToken != null && refreshToken != null && accessToken.getExpiresAt() != null) {
                accessTokenValue = accessToken.getTokenValue();
                refreshTokenValue = refreshToken.getTokenValue();
                accessTokenExpiresAt = accessToken.getExpiresAt().atZone(ZoneId.of("Asia/Taipei")).toLocalDateTime();
            }
        }

        return new TokenDto(accessTokenValue, refreshTokenValue, accessTokenExpiresAt);
    }
}
