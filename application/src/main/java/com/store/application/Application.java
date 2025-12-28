package com.store.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.store.*"})
@EntityScan(basePackages = {"com.store.*"})
@EnableJpaRepositories(basePackages = {"com.store.*"})
public class Application {

    public static void main(String[] args) {
        // Исправляем TimeZone до старта Spring Boot
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        // или: java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Europe/Kyiv"));

        SpringApplication.run(Application.class, args);
    }
}
