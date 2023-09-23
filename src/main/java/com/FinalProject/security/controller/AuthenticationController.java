package com.FinalProject.security.controller;

import com.FinalProject.security.model.AuthenticationRequest;
import com.FinalProject.security.model.RegisterRequest;
import com.FinalProject.security.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.FinalProject.exception.ExceptionHandler.setExceptionMessage;


@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;


    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        model.addAttribute("login", new AuthenticationRequest());
        return "login";
    }


    @GetMapping("/lms")
    public String loginLms() {
        return "redirect:login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("login") AuthenticationRequest request, HttpServletResponse response) {
        if (request == null)
            return "login";
        var token = service.authenticate(request);
        response.addCookie(new Cookie("jwt", token));
        return "redirect:/contact";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("register") RegisterRequest request) {
        service.register(request);
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerForm(Model model, HttpServletRequest request) {
        model.addAttribute("register", new RegisterRequest());
        setExceptionMessage(model, request);
        return "register";
    }


}
