package com.example.msscpizzaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsscPizzaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscPizzaServiceApplication.class, args);
    }

}
