package com.FinalProject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
@Slf4j
public class ContactController {
    @GetMapping
    public String list() {

        return "contact";
    }

}
