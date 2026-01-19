package com.store.application.controller;

import com.store.application.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class Test2Controller {
    private static final int waitSeconds = 4;
    private final TestService testService;

    @GetMapping
    public String test(){
        return "test";
    }

    @GetMapping("/t1")
    public String test1(@PathVariable String message){
       return testService.getResultWIthPause(waitSeconds, message);
    }
}
