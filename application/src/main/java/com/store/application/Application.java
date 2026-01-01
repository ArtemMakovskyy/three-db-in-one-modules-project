package com.store.application;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        exclude = DataSourceAutoConfiguration.class,
        scanBasePackages = {
                "com.store.application",
                "com.store.person",
                "com.store.sales",
                "com.store.ai",
                "com.store.security"
        }
)
public class Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Application.class, args);
    }
}
