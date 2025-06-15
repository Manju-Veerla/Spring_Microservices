package com.project;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {
	
	 @Bean
	    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
	        return builder.routes()
	                .route(r -> r.path("/api/users/**")
	                        .uri("lb://user-service"))
	                .route(r -> r.path("/api/department/**")
	                        .uri("lb://department-service"))
	                .route(r -> r.path("/api/auth/**")
	                        .uri("lb://auth-service"))
	                .build();
	    }


}
