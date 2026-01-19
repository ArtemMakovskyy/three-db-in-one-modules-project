package com.store.application.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @SneakyThrows
    public String getResultWIthPause(int sec, String message){
        Thread.sleep(sec * 1000L);
        return "success " + message + " wait for " + sec + " seconds";
    }
}
