package com.dev.accounts.controller;

import com.dev.accounts.constants.AccountsConstants;
import com.dev.accounts.dto.AccountsContactInfoDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.dto.ResponseDto;
import com.dev.accounts.service.IAccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "Account REST APIs.",
        description = "CRUD REST api for account."
)
public class AccountsController {

    Logger logger = LoggerFactory.getLogger(AccountsController.class);

    private final IAccountsService iAccountsService;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(IAccountsService iAccountsService, Environment environment, AccountsContactInfoDto accountsContactInfoDto) {
        this.iAccountsService = iAccountsService;
        this.environment = environment;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Operation(
            summary = "Creates Account.",
            description = "POST method to create new account."
    )
    @ApiResponse(
            responseCode = "201",
            description = "201 message account created."
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account information.",
            description = "GET method to fetch account."
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 message account."
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchCustomer(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Updates Account information.",
            description = "PUT method to update account."
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "201",
                description = "201 message account updated."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "500 internal server error.")
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Delete Account information.",
            description = "DELETE method to delete account."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "200 message account deleted."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "500 internal server error.")
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Fetch Build information.",
            description = "GET method to fetch build info."
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 message build info."
    )
    @GetMapping("/build-info")
    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    public ResponseEntity<String> getBuildInfo() {
        logger.debug("getBuildInfo(), build info requested.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        logger.debug("getBuildInfoFallback(), build info requested.");
        return ResponseEntity.status(HttpStatus.OK)
                .body("0.00");
    }

    @Operation(
            summary = "Fetch java version information.",
            description = "GET method to fetch java version info."
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 message java version info."
    )
    @RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Java 17");
    }

    @Operation(
            summary = "Fetch contact information.",
            description = "GET method to fetch contact info."
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 message contact info."
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
