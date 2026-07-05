package com.bloomie.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

@EnableJpaAuditing
public class
BloomiePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomiePlatformApplication.class, args);
    }

}
