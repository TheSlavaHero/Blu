package com.blubank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.blubank"})
public class BlubankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlubankApplication.class, args);
    }

}
