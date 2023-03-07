package com.example.msscpizzeriaeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MsscPizzeriaEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscPizzeriaEurekaApplication.class, args);
    }

}
