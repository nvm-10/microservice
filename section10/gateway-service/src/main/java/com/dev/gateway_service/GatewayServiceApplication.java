package com.dev.gateway_service;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

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
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
												.setFallbackUri("forward:/contactSupport")))
								.uri("lb://accounts"))
				.route(path ->
						path.path("/nvm10/loans/**")
								.filters( filter -> filter
										.rewritePath("/nvm10/loans/(?<segment>.*)",
												"/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.retry(retryConfig ->
												retryConfig.setRetries(3)
														.setMethods(HttpMethod.GET)
														.setBackoff(Duration.ofMillis(100),
																Duration.ofMillis(1000), 2, true)))
								.metadata(RESPONSE_TIMEOUT_ATTR, 200)
								.metadata(CONNECT_TIMEOUT_ATTR, 200)
								.uri("lb://loans"))
				.route(path ->
						path.path("/nvm10/card/**")
								.filters( filter -> filter
										.rewritePath("/nvm10/card/(?<segment>.*)",
												"/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.requestRateLimiter(config ->
												config.setRateLimiter(redisRateLimiter())
														.setKeyResolver(userKeyResolver())))
								.uri("lb://card"))
				.build();

	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(10)).build())
				.build());
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}

	@Bean
	RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1,1,1);
	}
}
