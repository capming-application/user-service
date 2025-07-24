package com.camping.userservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {

    @RequestMapping("/")
    public String home() {
        return "Welcome!";
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/login")
    public String login() {
        return "custom_login";
    }
}
