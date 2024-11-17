package com.example.hellospringintegration;

import com.example.hellospringintegration.dto.MyMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class HelloSpringIntegrationApplication {

    public static void main(String[] args) {
        MyMessage message = new MyMessage(1L, "Hello, World!");
        System.out.println(message);
        SpringApplication.run(HelloSpringIntegrationApplication.class, args);
    }
}
