package com.FinalProject.security.service;

import com.FinalProject.security.repository.TokenRepository;
import com.FinalProject.security.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtService service;
    private final UserRepository userRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("jwt")) {
                String jwt = service.extractToken(request);
                var storedToken = tokenRepository.findByToken(jwt).orElse(null);
                if (storedToken != null) {
                    storedToken.setExpired(true);
                    storedToken.setRevoked(true);
                    tokenRepository.save(storedToken);
                }
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                try {
                    response.sendRedirect("/");
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
