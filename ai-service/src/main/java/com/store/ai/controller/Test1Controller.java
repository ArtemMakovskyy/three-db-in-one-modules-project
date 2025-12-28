package com.store.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/test")
public class Test1Controller {
    @GetMapping
    public String test(){
        return "test ai";
    }
}
