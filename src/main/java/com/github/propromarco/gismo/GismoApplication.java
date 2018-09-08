package com.github.propromarco.gismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
public class GismoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GismoApplication.class, args);
    }

}
