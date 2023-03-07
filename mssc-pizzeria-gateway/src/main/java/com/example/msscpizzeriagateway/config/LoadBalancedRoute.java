package com.example.msscpizzeriagateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LoadBalancedRoute {

    @Bean
    public RouteLocator localRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/pizza*", "/api/v1/pizzaUpc/*")
                        .uri("lb://pizza-service"))
                .route(r -> r.path("/api/v1/customers**")
                        .uri("lb://pizza-order"))
                .route(r -> r.path("/api/v1/pizza/*/inventory")
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryCB")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")
                        ))
                        .uri("lb://pizza-inventory"))
                .route(r -> r.path("/inventory-failover")
                        .uri("lb://inventory-failover"))
                .build();
    }

}
