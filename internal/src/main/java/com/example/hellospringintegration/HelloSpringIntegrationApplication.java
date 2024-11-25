package com.example.hellospringintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class HelloSpringIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringIntegrationApplication.class, args);
    }
}
