package com.dev.accounts.clients;

import com.dev.accounts.dto.LoansDto;
import com.dev.accounts.fallbacks.CardFallback;
import com.dev.accounts.fallbacks.LoansFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping("/api/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("X-correlation-id") String correlationID,
                                                     @RequestParam String mobileNumber);
}
