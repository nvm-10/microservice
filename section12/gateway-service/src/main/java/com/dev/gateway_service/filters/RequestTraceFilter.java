package com.dev.gateway_service.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Configuration
public class RequestTraceFilter {

    Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    private final FilterUtility filterUtility;

    public RequestTraceFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Bean
    public GlobalFilter preGlobalFilter() {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            if(!isCorrelationIdPresent(headers)) {
                String correlationID = generateCorrelationId();
                exchange = filterUtility.setCorrelationId(exchange, correlationID);  // Important: reassign exchange
                logger.debug("X-correlation-id generated in RequestTraceFilter : {}", correlationID);
            } else {
                logger.debug("X-correlation-id found in RequestTraceFilter : {}",
                        filterUtility.getCorrelationId(headers));
            }
            return chain.filter(exchange);
        };
    }

    private boolean isCorrelationIdPresent(HttpHeaders headers) {
        String correlationId = filterUtility.getCorrelationId(headers);
        return correlationId != null && !correlationId.trim().isEmpty();
    }


    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
