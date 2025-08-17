package com.dev.accounts.service.impl;

import com.dev.accounts.constants.AccountsConstants;
import com.dev.accounts.dto.AccountsDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.entity.Accounts;
import com.dev.accounts.entity.Customer;
import com.dev.accounts.exception.CustomerAlreadyExistException;
import com.dev.accounts.exception.ResourceNotFoundException;
import com.dev.accounts.mapper.AccountsMapper;
import com.dev.accounts.mapper.CustomerMapper;
import com.dev.accounts.repository.AccountsRepository;
import com.dev.accounts.repository.CustomerRepository;
import com.dev.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository, CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        //check if customer already exist
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(customerOptional.isPresent()) {
            throw new CustomerAlreadyExistException("Customer already exist for given mobile number " +
                    customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy(System.getProperties().getProperty("user.name"));
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow( () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow( () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountDto(account, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts account = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber",
                            customerDto.getAccountsDto().getAccountNumber()));

            AccountsMapper.mapToAccounts(accountsDto, account);
            account = accountsRepository.save(account);
            long customerId = account.getCustomerId();

            Customer customer = customerRepository.findById(account.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId",
                            customerId));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow( () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        isDeleted = true;
        return isDeleted;
    }

    private Accounts createNewAccount(Customer savedCustomer) {
        Accounts account = new Accounts();
        account.setCustomerId(savedCustomer.getCustomerId());
        long randomAccountNumber = 100000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy(System.getProperties().getProperty("user.name"));
        return account;
    }




}
