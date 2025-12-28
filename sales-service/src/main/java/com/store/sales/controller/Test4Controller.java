package com.store.sales.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales/test")
public class Test4Controller {
    @GetMapping
    public String test(){
        return "test";
    }
}
