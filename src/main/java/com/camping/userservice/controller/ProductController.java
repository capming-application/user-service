package com.camping.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @GetMapping("/test")
    public String getProduct() {
        System.out.println("getProduct");
        return "Hello Terry";
    }
}