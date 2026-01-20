package com.store.application.controller;

import com.store.application.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Slf4j
public class ThreadController {
    private static final int waitSeconds = 1;
    private final ThreadService threadService;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    @GetMapping
    public String test() {
        return "test";
    }

    @GetMapping("/ts")
    public String sync(@RequestParam("message") String message) {
        String threadName = Thread.currentThread().getName();
        log.info("thread ({}), message sync {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
        return threadService.getResultWIthPause(waitSeconds, message);
    }

    @GetMapping("/tc")
    public Callable<String> callable(@RequestParam("message") String message) {
        String threadName = Thread.currentThread().getName();
        log.info("thread ({}), message callable {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
        return () -> threadService.getResultWIthPause(waitSeconds, message);
    }

    @GetMapping("/td")
    public DeferredResult<String> deferred(@RequestParam("message") String message) {
        DeferredResult<String> result = new DeferredResult<>();

        new Thread(() -> {
//            try {
            result.setResult(threadService.getResultWIthPause(waitSeconds, message));
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
        log.info("thread ({}), message CompletableFuture {}:{} ",
                threadName, atomicInteger.incrementAndGet(), message);
        return CompletableFuture.completedFuture(threadService.getResultWIthPause(waitSeconds, message));
    }

//    @GetMapping("/tm")
//    public Mono<String> mono(@RequestParam("message") String message) {
//        String threadName = Thread.currentThread().getName();
//        log.info("thread ({}), message {}:{} ", threadName, atomicInteger.incrementAndGet(), message);
//        return Mono.fromCallable(() -> {
//            return testService.getResultWIthPause(waitSeconds, message);
//        });
//    }

    @GetMapping("/all")
    public void startAll() {
        a();
        b();
        c();
        d();
    }

    private void a() {
        System.out.println("a");
        IntStream.range(0, 10)
                .parallel()
                .forEach(i -> this.sync("sync controller "));

    }
    private void b() {
        System.out.println("b");
        IntStream.range(0, 10)
                .parallel()
                .forEach(i -> this.callable("callable controller "));

    }
    private void c() {
        System.out.println("c");
        IntStream.range(0, 10)
                .parallel()
                .forEach(i -> this.deferred("deferred controller "));

    }
    private void d() {
        System.out.println("d");
        IntStream.range(0, 10)
                .parallel()
                .forEach(i -> this.cf("cf controller "));

    }
}
