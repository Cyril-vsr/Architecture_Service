package com.example.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route vers le User Service
                .route("user-service", r -> r.path("/users")
                        .uri("http://localhost:8081")) // User Service
                // Route vers le Request Service
                .route("request-service", r -> r.path("/requests")
                        .uri("http://localhost:8082")) // Request Service
                // Route vers le Feedback Service
                .route("feedback-service", r -> r.path("/feedbacks")
                        .uri("http://localhost:8083")) // Feedback Service
                .build();
    }
}

