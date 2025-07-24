package com.camping.userservice.service;

import com.camping.userservice.dto.LoginRsDto;
import com.camping.userservice.dto.RegisterRqDto;
import com.camping.userservice.entity.Users;
import com.camping.userservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final JwtService jwtService;
    private final UsersRepository usersRepository;

    public LoginRsDto loginWithOAuth2(OAuth2AuthenticationToken token) {
        String email = token.getPrincipal().getAttribute("email");
        String name = token.getPrincipal().getAttribute("name");
        Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
        String accessToken = jwtService.createAccessToken(name);
        String refreshToken = jwtService.createRefreshToken();
        this.jwtService.saveRefreshToken(email, refreshToken);

        Users savedUser = new Users();
        Optional<Users> usersOptional = this.usersRepository.findByEmail(email);
        if (usersOptional.isPresent()) {
            log.info("User already exists: {}", usersOptional.get().getEmail());
        } else {
            RegisterRqDto dto = new RegisterRqDto(email, name, null);
            savedUser = this.register(dto);
        }

        return new LoginRsDto(savedUser.getEmail(), savedUser.getUsername(), accessToken, refreshToken, accessTokenExpiresAt);
    }

    public LoginRsDto loginWithGeneral() {
        return null;
    }

    public Users register(RegisterRqDto registerRqDto) {
        Users user = new Users();
        user.setEmail(registerRqDto.email());
        user.setUsername(registerRqDto.username());
        user.setPasswordHash(registerRqDto.password());
        return this.usersRepository.save(user);
    }
}
