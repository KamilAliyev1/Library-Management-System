package com.FinalProject.service.impl;

import com.FinalProject.exception.EmailAlreadyFoundException;
import com.FinalProject.security.AuthenticationRequest;
import com.FinalProject.model.Role;
import com.FinalProject.model.User;
import com.FinalProject.repository.UserRepository;
import com.FinalProject.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private final JwtService service;

    private final AuthenticationManager manager;
    private final TokenRepository tokenRepository;


    public String register(RegisterRequest request) {
        var email = repository.findByEmail(request.getEmail());
        if (email.isPresent())
            throw new EmailAlreadyFoundException("Email already found " + request.getEmail());
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = service.generateToken(user);
        saveUserToken(user, jwtToken);
        return jwtToken;
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public String authenticate(AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFound("Email or Password is wrong"));
        revokeAllUserTokens(user);
        var jwtToken = service.generateToken(user);
        saveUserToken(user, jwtToken);
        return jwtToken;
    }

}
