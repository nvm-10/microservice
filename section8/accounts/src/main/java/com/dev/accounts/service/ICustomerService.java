package com.dev.accounts.service;

import com.dev.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto getCustomerDetails(String mobileNumber);
}
