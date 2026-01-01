package com.store.person.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person/test")
public class Test3Controller {

    @GetMapping
    @PreAu
    public String test(){
        return "test";
    }
}
