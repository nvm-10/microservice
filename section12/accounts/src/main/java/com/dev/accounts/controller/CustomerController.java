package com.dev.accounts.controller;

import com.dev.accounts.dto.CustomerDetailsDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "CustomerDetails REST APIs.",
        description = "CRUD REST api for customer details."
)
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Fetch Customer Details information.",
            description = "GET method to fetch customer infor."
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 message customer details."
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchAccount(@RequestHeader("X-correlation-id")
                                        String correlationID,
                                    @RequestParam String mobileNumber) {
        //logger.debug("X-correlation-id found : {}.", correlationID);
        logger.info("Fetching Customer Details information.");
        CustomerDetailsDto customerDetailsDto = customerService.getCustomerDetails(mobileNumber,correlationID);
        logger.info("Fetched Customer Details information.");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
