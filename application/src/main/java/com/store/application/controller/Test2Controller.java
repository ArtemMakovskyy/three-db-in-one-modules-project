package com.store.application.controller;

import com.store.application.service.TestService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Slf4j
public class Test2Controller {
    private static final int waitSeconds = 4;
    private final TestService testService;
    public AtomicInteger atomicInteger = new AtomicInteger(0);

    @GetMapping
    public String test() {
        return "test";
    }

    @GetMapping("/ts")
    public String sync(@RequestParam("message") String message) {
        String threadName = Thread.currentThread().getName();
        log.info("thread ({}), message sync {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
        return testService.getResultWIthPause(waitSeconds, message);
    }

    @GetMapping("/tc")
    public Callable<String> callable(@RequestParam("message") String message) {
        String threadName = Thread.currentThread().getName();
        log.info("thread ({}), message callable {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
        return () -> testService.getResultWIthPause(waitSeconds, message);
    }

    @GetMapping("/td")
    public DeferredResult<String> deferred(@RequestParam("message") String message) {
        DeferredResult<String> result = new DeferredResult<>();

        new Thread(() -> {
//            try {
                result.setResult(testService.getResultWIthPause(waitSeconds, message));
                String threadName = Thread.currentThread().getName();
                log.info("thread ({}), message DeferredResult {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
//            } catch (Exception e) {
//                result.setErrorResult(e);
//            }
        }).start();

        return result;
    }

    @GetMapping("/tcf")
    public CompletableFuture<String> cf(@RequestParam("message") String message) {
        String threadName = Thread.currentThread().getName();
        log.info("thread ({}), message CompletableFuture {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
        return CompletableFuture.completedFuture(testService.getResultWIthPauseAsync(waitSeconds, message));
    }

//    @GetMapping("/tm")
//    public Mono<String> mono(@RequestParam("message") String message) {
//        String threadName = Thread.currentThread().getName();
//        log.info("thread ({}), message {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
//        return Mono.fromCallable(() -> {
//            return testService.getResultWIthPause(waitSeconds, message);
//        });
//    }
}
