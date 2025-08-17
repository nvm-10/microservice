package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Account details of the customer"
)
public class AccountsDto {

    @Schema(
            description = "Account number of the customer"
    )
    private Long accountNumber;

    @Schema(
            description = "Account type of the customer"
    )
    private String accountType;

    @Schema(
            description = "Bank address of the customer"
    )
    private String branchAddress;
}

