package com.dev.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(path ->
						path.path("/nvm10/accounts/**")
								.filters( filter -> filter
										.rewritePath("/nvm10/accounts/(?<segment>.*)",
												"/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.uri("lb://accounts"))
				.route(path ->
						path.path("/nvm10/loans/**")
								.filters( filter -> filter
										.rewritePath("/nvm10/loans/(?<segment>.*)",
												"/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.uri("lb://loans"))
				.route(path ->
						path.path("/nvm10/card/**")
								.filters( filter -> filter
										.rewritePath("/nvm10/card/(?<segment>.*)",
												"/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.uri("lb://card"))
				.build();

	}

}
