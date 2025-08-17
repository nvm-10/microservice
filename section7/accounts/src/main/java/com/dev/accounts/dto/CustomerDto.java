package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema holds information about customer and account."
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer", example = "test_user"
    )
    private String name;

    @Schema(
            description = "Email of the customer", example = "test@Test.com"
    )
    private String email;

    @Schema(
            description = "Mobile number of the customer", example = "9090909090"
    )
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    private AccountsDto accountsDto;
}

