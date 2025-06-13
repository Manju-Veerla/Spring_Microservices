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
	                        .uri("http://user-service:9002/"))
	                .route(r -> r.path("/api/department/**")
	                        .uri("http://department-service:9001/"))
	                /*.route(r -> r.path("/api/products/**")
	                        .uri("http://localhost:8083/"))
	                */.build();
	    }


}
