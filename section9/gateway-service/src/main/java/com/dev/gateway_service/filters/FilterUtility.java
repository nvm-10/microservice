package com.dev.gateway_service.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "X-correlation-id";

    public String getCorrelationId(HttpHeaders headers) {
        if(headers.getFirst(CORRELATION_ID) != null) {
            return headers.getFirst(CORRELATION_ID);
        } else {
            return null;
        }
    }

    public ServerWebExchange setRequestHeaders(ServerWebExchange serverWebExchange,
                                                  String name, String value) {
        return serverWebExchange.mutate()
                .request(serverWebExchange.getRequest()
                        .mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange serverWebExchange, String correlationId) {
        return this.setRequestHeaders(serverWebExchange, CORRELATION_ID, correlationId);
    }

}
