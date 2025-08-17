package com.dev.accounts.service.impl;

import com.dev.accounts.clients.CardFeignClient;
import com.dev.accounts.clients.LoansFeignClient;
import com.dev.accounts.dto.*;
import com.dev.accounts.entity.Accounts;
import com.dev.accounts.entity.Customer;
import com.dev.accounts.exception.ResourceNotFoundException;
import com.dev.accounts.mapper.AccountsMapper;
import com.dev.accounts.mapper.CustomerMapper;
import com.dev.accounts.repository.AccountsRepository;
import com.dev.accounts.repository.CustomerRepository;
import com.dev.accounts.service.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardFeignClient cardFeignClient;
    private final LoansFeignClient loansFeignClient;

    public CustomerServiceImpl(AccountsRepository accountsRepository, CustomerRepository customerRepository, CardFeignClient cardFeignClient, LoansFeignClient loansFeignClient) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.cardFeignClient = cardFeignClient;
        this.loansFeignClient = loansFeignClient;
    }


    @Override
    public CustomerDetailsDto getCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow( () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow( () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId()));
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountDto(account, new AccountsDto()));

        ResponseEntity<CardDto> cardDtoResponseEntity = cardFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
}
