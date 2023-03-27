package com.example.msscpizzainventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsscPizzaInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscPizzaInventoryApplication.class, args);
    }

}
