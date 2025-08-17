package com.dev.accounts.clients;

import com.dev.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("card")
public interface CardFeignClient {

    @GetMapping("/api/fetch")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber);
}
