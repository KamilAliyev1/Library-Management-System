package com.FinalProject.security.service;

import com.FinalProject.model.Role;
import com.FinalProject.security.exception.EmailAlreadyFoundException;
import com.FinalProject.security.exception.UserNotFound;
import com.FinalProject.security.model.*;
import com.FinalProject.security.repository.TokenRepository;
import com.FinalProject.security.repository.UserRepository;
import com.FinalProject.service.FIleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private final JwtService service;

    private final AuthenticationManager manager;
    private final TokenRepository tokenRepository;
    private final FIleService fIleService;

//    private final UserService userService;


    public void register(RegisterRequest request) {
        repository.findByEmail(request.getEmail()).ifPresentOrElse
                (
                        user -> {
                            throw new EmailAlreadyFoundException("Email already found " + request.getEmail());
                        },
                        () -> {
                            var user = User.builder()
                                    .firstname(request.getFirstname())
                                    .lastname(request.getLastname())
                                    .email(request.getEmail())
                                    .password(encoder.encode(request.getPassword()))
                                    .role(Role.ROLE_USER)
                                    .image(request.getFile().getOriginalFilename())
                                    .build();
                            fIleService.save(request.getFile());
                            repository.save(user);
                            var jwtToken = service.generateToken(user);
                            saveUserToken(user, jwtToken);
                        }
                );
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
                () -> new UserNotFound("Email or password wrong"));

//        user.setIsActive(true);
//        userService.getUserImage(user);
        revokeAllUserTokens(user);

        var jwtToken = service.generateToken(user);
        saveUserToken(user, jwtToken);
        return jwtToken;
    }

}
