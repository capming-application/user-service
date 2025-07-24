package com.camping.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/", "/login").permitAll();
                    registry.anyRequest().authenticated();
                })
                .oauth2Login(oauth2login -> oauth2login
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String provider = "";
                            if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
                                provider = oauthToken.getAuthorizedClientRegistrationId();
                            }

                            response.sendRedirect("/auth/oauth2-login/" + provider);
                        })
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder()))
                )
                .build();
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        String secret = "PpqJ1HufqS/bUjdWqpVdxvOq8Hu6Vnv3gk4q7ZqL1RU=";
        return NimbusJwtDecoder.withSecretKey(
                new SecretKeySpec(secret.getBytes(), "HmacSHA256")
        ).build();
    }
}
