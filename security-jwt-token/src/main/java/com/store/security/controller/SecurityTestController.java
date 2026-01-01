package com.store.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {

    @GetMapping
    public String index() {
        return "index";
    }
}
