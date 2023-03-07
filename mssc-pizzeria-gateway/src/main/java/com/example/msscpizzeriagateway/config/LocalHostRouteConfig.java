package com.example.msscpizzeriagateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!local-discovery")
@Configuration
public class LocalHostRouteConfig {

    @Bean
    public RouteLocator localRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/pizza*", "/api/v1/pizzaUpc/*")
                        .uri("http://localhost:8080"))
                .route(r -> r.path("/api/v1/customers**")
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/api/v1/pizza/*/inventory")
                        .uri("http://localhost:8082"))
                .build();
    }

}
