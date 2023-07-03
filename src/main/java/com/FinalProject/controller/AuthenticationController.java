package com.FinalProject.controller;

import com.FinalProject.security.AuthenticationRequest;
import com.FinalProject.security.RegisterRequest;
import com.FinalProject.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("login", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("login") AuthenticationRequest request) {
        if (request == null)
            return "login";
        var token = service.authenticate(request);
        System.out.println(token);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        RequestEntity<String> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create("htt://localhost:8080/about"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("register") RegisterRequest request) {
        if (request == null) {
            return "register";
        }
        service.register(request);
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("register", new RegisterRequest());
        return "register";
    }


//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(@ModelAttribute("request") AuthenticationRequest request) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }


}
