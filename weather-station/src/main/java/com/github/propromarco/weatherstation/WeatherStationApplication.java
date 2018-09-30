package com.github.propromarco.weatherstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
public class WeatherStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherStationApplication.class, args);
    }

}
