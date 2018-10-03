package com.github.propromarco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
public class GismoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GismoApplication.class, args);
    }

}
