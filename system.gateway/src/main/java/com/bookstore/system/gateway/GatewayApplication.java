package com.bookstore.system.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator route(RouteLocatorBuilder builder){
		return builder
				.routes()
					.route(r -> r.path("/api/users/**").uri("lb://userservice"))
					.route(r -> r.path("/api/auth/**").uri("lb://userservice"))
					.route(r -> r.path("/api/library/**").uri("lb://libraryservice"))
				.build();
	}
}
