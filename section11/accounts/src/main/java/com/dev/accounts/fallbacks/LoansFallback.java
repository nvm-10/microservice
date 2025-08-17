package com.dev.accounts.fallbacks;

import com.dev.accounts.clients.LoansFeignClient;
import com.dev.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationID, String mobileNumber) {
        return null;
    }
}
