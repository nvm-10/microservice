package com.dev.accounts.fallbacks;

import com.dev.accounts.clients.CardFeignClient;
import com.dev.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardFallback implements CardFeignClient {

    @Override
    public ResponseEntity<CardDto> fetchCardDetails(String correlationID, String mobileNumber) {
        return null;
    }
}
