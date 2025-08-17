package com.dev.accounts.clients;

import com.dev.accounts.dto.CardDto;
import com.dev.accounts.fallbacks.CardFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card", fallback = CardFallback.class)
public interface CardFeignClient {

    @GetMapping("/api/fetch")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestHeader("X-correlation-id") String correlationID,
                                                    @RequestParam String mobileNumber);
}
